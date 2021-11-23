package com.ldp.jetpack_app;

import android.app.Application;
import android.content.Context;

import com.ldp.jetpack_app.jetpack.datastore.DataStoreUtil;
import com.ldp.jetpack_app.jetpack.room.DatabaseUtil;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/23
 * <p>
 * Summary:
 */
public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        DataStoreUtil.Companion.init(this);
        DatabaseUtil.INSTANCE.initDataBase();
    }
}
