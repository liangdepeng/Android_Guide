package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;
import android.util.Printer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.example.common_module.AppContext;
import cn.example.common_module.JumpServiceFactory;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/2
 * <p>
 * Summary: Application
 */
public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private long lastMills = System.currentTimeMillis();

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        // 组建通信初始化
        JumpServiceFactory.INSTANCE.init(AppComponent.class.getName());
        // application context
        AppContext.init(context);
        // 监控卡顿
        Looper.getMainLooper().setMessageLogging(new Printer() {
            @Override
            public void println(String x) {
                long current = System.currentTimeMillis();
                Log.e("MessageLogging", "time : "+(current - lastMills) + " ms   message: " + x);
                lastMills=current;
            }
        });

        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                // Loop 所绑定的线程 空闲时 调用此方法

                // 延迟初始化操作等等

                // 如果需要循环执行 返回true 空闲时会一直执行 直到返回 false
                return false;
            }
        });

        // 监控所有activity 生命周期
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity,
                                                    @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    public static Context getAppContext() {
        return context;
    }
}
