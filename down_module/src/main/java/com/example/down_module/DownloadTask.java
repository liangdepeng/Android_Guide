package com.example.down_module;

import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/10/26
 * <p>
 * Summary:
 */
public class DownloadTask implements Runnable {

    private MultiThreadManager multiThreadManager;
    private URL url;
    private File saveFile;
    private int block;
    private int downLength;
    private int threadId;
    private boolean finish = false;         //该线程是否完成下载的标志

    public DownloadTask(MultiThreadManager multiThreadManager, URL url, File saveFile, int block, int downLength, int threadId) {
        this.multiThreadManager = multiThreadManager;
        this.url = url;
        this.saveFile = saveFile;
        this.block = block;
        this.downLength = downLength;
        this.threadId = threadId;
    }

    @Override
    public void run() {
        if (downLength < block) {
            Log.e("mutilThread", " 下载开始" + Thread.currentThread().toString());
            // 下载未完成
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(8000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "*/*");
                connection.setRequestProperty("Accept-Language", "zh-CN");
                connection.setRequestProperty("Referer", url.toString());
                connection.setRequestProperty("Charset", "UTF-8");
                int startPos = block * threadId + downLength; //开始位置
                int endPos = block * (threadId + 1); //结束位置
                connection.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos); //设置获取实体数据的范围
                connection.setRequestProperty("Connection", "Keep-Alive");

                InputStream inputStream = connection.getInputStream();
                byte[] bytes = new byte[1024];
                int len = 0;

                RandomAccessFile accessFile = new RandomAccessFile(saveFile, "rwd");
                accessFile.seek(startPos);
                //用户没有要求停止下载,同时没有达到请求数据的末尾时会一直循环读取数据
                while (!multiThreadManager.isExited() && (len = inputStream.read(bytes)) != -1) {
                    //直接把数据写入到文件中
                    accessFile.write(bytes, 0, len);
                    //把新线程已经写到文件中的数据加入到下载长度中
                    downLength += len;
                    //把该线程已经下载的数据长度更新到数据库和内存哈希表中
                    multiThreadManager.update(threadId, downLength);
                    //把新下载的数据长度加入到已经下载的数据总长度中
                    multiThreadManager.append(len);
                }
                accessFile.close();
                inputStream.close();
                finish = true;     //设置完成标记为true,无论下载完成还是用户主动中断下载
                Log.e("mutilThread", " 下载结束" + Thread.currentThread().toString());
            } catch (Exception e) {
                this.downLength = -1;               //设置该线程已经下载的长度为-1
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载是否完成
     *
     * @return
     */
    public boolean isFinish() {
        return finish;
    }

    /**
     * 已经下载的内容大小
     *
     * @return 如果返回值为-1,代表下载失败
     */
    public long getDownLength() {
        return downLength;
    }
}
