package com.example.down_module;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/10/26
 * <p>
 * Summary:
 */
public interface IDownloadListener {

    @MainThread
    void onPreDownload();

    @WorkerThread
    void onDownloading();

    void onDownFinished();

    void onError(String message);


    void onProgressUpdate(int progress);
}
