package com.example.myapplication.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/2
 * <p>
 * Summary: BaseActivity
 */
public class BaseActivity extends AppCompatActivity {

    private final String tag = "activity_lifecycle";
    private Handler handler;
    private Runnable showRunnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(tag, "--onCreate-- " + getClass().getSimpleName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(tag, "--onStart-- " + getClass().getSimpleName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(tag, "--onResume-- " + getClass().getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(tag, "--onPause-- " + getClass().getSimpleName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(tag, "--onStop-- " + getClass().getSimpleName());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(tag, "--onRestart-- " + getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
        Log.e(tag, "--onDestroy-- " + getClass().getSimpleName());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(tag, "--onSaveInstanceState-- " + getClass().getSimpleName());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(tag, "--onRestoreInstanceState-- " + getClass().getSimpleName());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.e(tag, "--onNewIntent-- " + getClass().getSimpleName());
    }

    protected ProgressDialog progressDialog;

    public void showProgressDialog(String tips) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("提示");
            progressDialog.setMessage(tips);
            progressDialog.setCanceledOnTouchOutside(false);
        }

        if (progressDialog.isShowing())
            return;

        if (showRunnable == null) {
            showRunnable = new Runnable() {
                @Override
                public void run() {
                    progressDialog.show();
                }
            };
        }

        runOnUiThread(showRunnable);
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public final Handler getMainHandler() {
        if (handler == null)
            handler = new Handler(Looper.getMainLooper());
        return handler;
    }

    //
    public final boolean isUiThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
