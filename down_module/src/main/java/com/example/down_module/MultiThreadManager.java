package com.example.down_module;

import android.os.Build;
import android.util.Log;

import com.example.down_module.util.DownloadDao;
import com.example.down_module.util.FileUtil;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import abc.common.util.KtExpandUtil;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/10/26
 * <p>
 * Summary: 多线程下载
 */
public class MultiThreadManager {


    private String tag = "MultiThreadDownloadManager";
    private DownloadDao downloadDao;
    // 下载中断、停止
    private boolean exited;
    // 已经下载的文件长度
    private int downloadedSize;
    // 开始下载的文件长度
    private long fileSize;
    // 线程池
    private ThreadPoolExecutor executor;
    // 保存文件
    private File file;
    // 缓存每个线程的下载长度
    private Map<Integer, Integer> data = new ConcurrentHashMap<>();
    // 每个人线程的下载长度
    private int block;
    // 下载路径
    private String downloadUrl;

    private List<DownloadTask> runnables;

    private IDownloadListener listener;

    public MultiThreadManager() {
        runnables = new ArrayList<>();
        this.downloadDao = new DownloadDao();
        executor = new ThreadPoolExecutor(0, 10, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    }

    public void downObj(String objUrl, IDownloadListener listener) {
        KtExpandUtil.Companion.showToast("下载开始");
        this.listener = listener;
        this.downloadUrl = objUrl;
        Runnable runnable = () -> {
            try {
                URL url = new URL(downloadUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(8000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "*/*");// 设置客户端可接受的文件类型
                connection.setRequestProperty("Accept-Language", "zh-CN");
                connection.setRequestProperty("Referer", downloadUrl);// 请求来源
                connection.setRequestProperty("Charset", "UTF-8");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.connect();
                // 获取请求体 header
                printResponseHeader(connection);

                if (connection.getResponseCode() == 200) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        fileSize = connection.getContentLengthLong();
                    } else {
                        fileSize = connection.getContentLength();
                    }

                    if (fileSize <= 0) {
                        throw new Exception("文件大小异常");
                    }
                    // 获取文件名
                    String fileName = getFileName(connection);
                    // 保存文件

                    FileUtil fileUtil = new FileUtil();
                    file = fileUtil.createFile(fileName);

                    HashMap<Integer, Integer> map = downloadDao.getDownloadProgressData(downloadUrl);

                    if (map.size() > 0) {
                        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                            data.put(entry.getKey(), entry.getValue());
                            downloadedSize += entry.getValue();
                        }
                        print(downloadedSize + " ");
                    } else {
                        data.clear();
                        for (int i = 0; i < 3; i++) {
                            data.put(i, 0);
                        }
                        downloadedSize = 0;
                    }

                    block = (int) (fileSize % 3 == 0 ? fileSize / 3 : fileSize / 3 + 1);

                    download();

                }

            } catch (Exception e) {
                KtExpandUtil.Companion.showToast(e.getMessage());
                e.printStackTrace();
            }
        };
        executor.execute(runnable);
    }

    private void download() {
        try {
            RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
            if (fileSize > 0) {
                accessFile.setLength(fileSize);
                accessFile.close();
            }

            URL url = new URL(downloadUrl);

            for (int i = 0; i < 3; i++) {
                int downLength = data.get(i);
                //通过特定的线程id获取该线程已经下载的数据长度
                //判断线程是否已经完成下载,否则继续下载
                if (downLength < block && downloadedSize < fileSize) {
                    // 初始化特定线程runnable对象
                    DownloadTask task = new DownloadTask(this, url, file, block, downLength, i);
                    runnables.add(task);
                    executor.execute(task);
                }
            }

            //如果存在下载记录，删除它们，然后重新添加
            downloadDao.delete(downloadUrl);
            downloadDao.save(downloadUrl, data);

            //下载未完成
            boolean notFinish = true;

            while (notFinish) {
                Thread.sleep(500);
                notFinish = false;
                for (int i = 0; i < runnables.size(); i++) {
                    if (runnables.get(i) != null && !runnables.get(i).isFinish()) {
                        notFinish = true;

                        // todo fixing 失败处理

                    }
                }
                if (listener != null) {
                    listener.onProgressUpdate(downloadedSize);
                }
            }

            //下载完成删除记录
            if (downloadedSize == fileSize) {
                downloadDao.delete(downloadUrl);
            }

            KtExpandUtil.Companion.showToast("下载完成");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取文件名
     */
    private String getFileName(HttpURLConnection conn) {
        //从下载的路径的字符串中获取文件的名称
        String filename = this.downloadUrl.substring(this.downloadUrl.lastIndexOf('/') + 1);
        if (filename == null || "".equals(filename.trim())) {     //如果获取不到文件名称
            for (int i = 0; ; i++)  //使用无限循环遍历
            {
                String mine = conn.getHeaderField(i);     //从返回的流中获取特定索引的头字段的值
                if (mine == null)
                    break;          //如果遍历到了返回头末尾则退出循环
                //获取content-disposition返回字段,里面可能包含文件名
                if ("content-disposition".equals(conn.getHeaderFieldKey(i).toLowerCase())) {
                    //使用正则表达式查询文件名
                    Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase());
                    if (m.find())
                        return m.group(1);    //如果有符合正则表达式规则的字符串,返回
                }
            }
            filename = UUID.randomUUID() + ".tmp";//如果都没找到的话,默认取一个文件名
            //由网卡标识数字(每个网卡都有唯一的标识号)以及CPU时间的唯一数字生成的一个16字节的二进制作为文件名
        }
        return filename;
    }

    /**
     * 获取Http响应头字段
     *
     * @param http
     * @return
     */
    public static Map<String, String> getHttpResponseHeader(HttpURLConnection http) {
        //使用LinkedHashMap保证写入和便利的时候的顺序相同,而且允许空值
        Map<String, String> header = new LinkedHashMap<String, String>();
        //此处使用无线循环,因为不知道头字段的数量
        for (int i = 0; ; i++) {
            String mine = http.getHeaderField(i);  //获取第i个头字段的值
            if (mine == null)
                break;      //没值说明头字段已经循环完毕了,使用break跳出循环
            header.put(http.getHeaderFieldKey(i), mine); //获得第i个头字段的键
        }
        return header;
    }

    /**
     * 打印Http头字段
     *
     * @param http
     */
    public void printResponseHeader(HttpURLConnection http) {
        //获取http响应的头字段
        Map<String, String> header = getHttpResponseHeader(http);
        //使用增强for循环遍历取得头字段的值,此时遍历的循环顺序与输入树勋相同
        for (Map.Entry<String, String> entry : header.entrySet()) {
            //当有键的时候则获取值,如果没有则为空字符串
            String key = entry.getKey() != null ? entry.getKey() + ":" : "";
            print(key + entry.getValue());      //打印键和值得组合
        }
    }

    /**
     * 打印信息
     *
     * @param msg 信息字符串
     */
    private void print(String msg) {
        Log.e(tag, msg);
    }

    public void exit() {
        this.exited = true;
    }

    public boolean isExited() {
        return exited;
    }

    public long getFileSize() {
        return fileSize;
    }

    /**
     * 文件累计下载的大小
     */
    protected synchronized void append(int size) {
        downloadedSize += size;
    }

    /**
     * 更新指定线程最后下载的位置
     *
     * @param threadId 线程id
     * @param progress 最后下载的位置
     */
    protected synchronized void update(int threadId, int progress) {
        //把指定线程id的线程赋予最新的下载长度,以前的值会被覆盖掉
        data.put(threadId, progress);
        //更新数据库中制定线程的下载长度
        downloadDao.update(downloadUrl, threadId, progress);
    }
}
