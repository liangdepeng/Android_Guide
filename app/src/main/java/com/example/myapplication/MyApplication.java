package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.example.commom_module.AppMoudleConfig;
import com.example.commom_module.IAppComponent;
import com.example.commom_module.JumpServiceFactory;

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
        // 组建通信初始化
        JumpServiceFactory.INSTANCE.init(AppComponent.class.getName());
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
