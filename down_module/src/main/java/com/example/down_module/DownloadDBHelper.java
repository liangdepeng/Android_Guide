package com.example.down_module;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/10/26
 * <p>
 * Summary:
 */
public class DownloadDBHelper extends SQLiteOpenHelper {


    public DownloadDBHelper(@Nullable Context context) {
        super(context, "download.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //数据库的结构为:表名:filedownlog 字段:id,downpath:当前下载的资源,
        //threadid:下载的线程id，downlength:线程下载的最后位置
        db.execSQL("create table if not exists filedownlog" +
                "(id integer primary key autoincrement," +
                "downpath varchar(100)," +
                "threadid integer,downlength integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //当版本号发生改变时调用该方法,这里删除数据表,在实际业务中一般是要进行数据备份的
        db.execSQL("drop table if exists filedownlog");
        onCreate(db);
    }
}
