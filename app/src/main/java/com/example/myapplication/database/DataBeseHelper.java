package com.example.myapplication.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.MyApplication;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/6
 * <p>
 * Summary: 创建数据库 初始化
 */
public class DataBeseHelper extends SQLiteOpenHelper {

    public static DataBeseHelper getInstance() {
        return Instance.dataBaseHelper;
    }

    private DataBeseHelper() {
        super(MyApplication.getAppContext(), "word_table", null, 1);
    }

    private final static class Instance {
        private final static DataBeseHelper dataBaseHelper = new DataBeseHelper();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE words(id integer primary key autoincrement," +
                "word varchar(60)," +
                "en_pronunciation varchar(255)," +
                "us_pronunciation varchar(255)," +
                "translate,isCollect)");

        db.execSQL("CREATE TABLE users(id integer primary key autoincrement," +
                "userid varchar(30)," +
                "name varchar(30)," +
                "password varchar(30)," +
                "user_logo varchar(255)," +
                "personal_txt varchar(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void deleteAll(){
        SQLiteDatabase sqLiteDatabase = getInstance().getWritableDatabase();
        sqLiteDatabase.execSQL("delete from word_table");
        sqLiteDatabase.close();
    }
}
