package com.example.down_module;

import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.example.down_module.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import abc.common.util.KtExpandUtil;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/10/22
 * <p>
 * Summary: 普通的单线程下载
 */
public class SingleThreadManager {

    private final ExecutorService threadPool;
    private final Handler handler;
    private final int Android_Q = 29;

    public SingleThreadManager() {
        threadPool = Executors.newCachedThreadPool();
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 目标API级别为27或更低的应用程序的默认值为“ true”。
     * 面向API级别28或更高级别的应用默认为“ false”。
     * 清单文件加上 usesCleartextTraffic=true 允许 http明文访问链接
     *
     * @param url 文件url 目前只下载MP4存储到SD卡兼容 29以上 其他文件类型加上判断即可
     */
    public void downloadObj(String url, IDownloadListener listener) {
        KtExpandUtil.Companion.showToast("下载开始");

        if (listener != null) {
            listener.onPreDownload();
        }

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    checkNotNull(url);

                    if (listener != null)
                        listener.onDownloading();

                    URL fileUrl = new URL(url);
                    // 打开到此URL的连接并返回一个InputStream以从该连接读取。
                    InputStream inputStream = fileUrl.openStream();
                    // 截取文件名类型 .mp3/.mp4/.flv 等等
                    String fileName = System.currentTimeMillis() + "_" + url.substring(url.lastIndexOf("/") + 1);
                    // 获取文件名
                    FileUtil fileUtil = new FileUtil(Environment.DIRECTORY_MOVIES);
                    File file = fileUtil.createFile(fileName);
                    // 读取返回的文件流
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    // 写入文件流
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, len);
                    }

                    inputStream.close();
                    fileOutputStream.close();

                    // 大于等于android 29 需要 复制文件插入媒体库
                    if (Build.VERSION.SDK_INT >= Android_Q) {
                        fileUtil.copyFileToSdCardForAndroidQ(file);
                    }

                    if (listener != null) {
                        handler.post(listener::onDownFinished);
                    }

                    KtExpandUtil.Companion.showToast("下载完成");

                } catch (Exception e) {
                    if (listener != null)
                        listener.onError(e.getMessage());
                    KtExpandUtil.Companion.showToast(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    public void downloadObj2(String objUrl) {
        try {
            checkNotNull(objUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkNotNull(String url) throws Exception {
        if (url == null || url.length() == 0)
            throw new IllegalArgumentException("url 不能为空");
    }

}
