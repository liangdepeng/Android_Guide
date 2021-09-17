package com.example.commom_module;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/8
 * <p>
 * Summary: 全局获取 application Context
 */
public class AppContext {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static void init(Context mContext) {
        context = mContext;
    }

    public static Context getAppContext() {
        return context;
    }
}
