package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import cn.example.common_module.AppContext;
import cn.example.common_module.JumpServiceFactory;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/2
 * <p>
 * Summary:
 */
public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        // 组建通信初始化
        JumpServiceFactory.INSTANCE.init(AppComponent.class.getName());
        // application context
        AppContext.init(context);
    }

    public static Context getAppContext() {
        return context;
    }
}
