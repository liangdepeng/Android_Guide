package com.example.down_module;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

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
public class SingleThreadDownloadManager {

    private final ExecutorService threadPool;
    private final Handler handler;

    public SingleThreadDownloadManager() {
        threadPool = Executors.newCachedThreadPool();
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 目标API级别为27或更低的应用程序的默认值为“ true”。
     * 面向API级别28或更高级别的应用默认为“ false”。
     * 清单文件加上 usesCleartextTraffic=true 允许 http明文访问链接
     *
     * @param url
     */
    public void downloadObj(String url) {

        KtExpandUtil.Companion.showToast("下载开始");

        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL fileUrl = new URL(url);
                    // 打开到此URL的连接并返回一个InputStream以从该连接读取。
                    InputStream inputStream = fileUrl.openStream();
                    // 截取文件名类型 .mp3/.mp4/.flv 等等
                    String fileName = System.currentTimeMillis() + "_" + url.substring(url.lastIndexOf("/")+1);

                    FileUtil fileUtil = new FileUtil();
                    File file = fileUtil.createFile(fileName);

                    FileOutputStream fileOutputStream = new FileOutputStream(file);

                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, len);
                    }

                    inputStream.close();
                    fileOutputStream.close();

                    if (Build.VERSION.SDK_INT >= 29) {
                        fileUtil.copyFileToSdCard(file);
                    }

                    KtExpandUtil.Companion.showToast("下载完成");

                } catch (Exception e) {
                    KtExpandUtil.Companion.showToast(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

}
