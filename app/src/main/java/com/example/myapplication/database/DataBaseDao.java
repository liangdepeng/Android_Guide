package com.example.myapplication.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.myapplication.bean.WordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/6
 * <p>
 * Summary: 数据库操作业务封装
 */
public enum DataBaseDao {
    // instance
    INSTANCE;

    private final static String DB_NAME = "words";

    private DataBeseHelper getDataBase() {
        return DataBeseHelper.getInstance();
    }

    public boolean insertBySqlStr(String sqlLine){
        try {
            //为了匹配数据库字段进行增减 删除转义字符 纠正数据库 表字段 ",'0','0')"
            sqlLine = sqlLine.substring(0, sqlLine.length() - 2) + ",'0')" ;
            sqlLine = sqlLine.replace("\\'", "");
            SQLiteDatabase database = getDataBase().getWritableDatabase();
            database.execSQL(sqlLine);
            database.close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 增加一条
     */
    public void insertWord(WordBean wordBean) {
        if (wordBean == null || TextUtils.isEmpty(wordBean.word))
            return;
        SQLiteDatabase sqLiteDatabase = getDataBase().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word", wordBean.word);
        values.put("translate", wordBean.translate);
        values.put("isCollect", wordBean.isCollect);
        sqLiteDatabase.insert(DB_NAME, null, values);
        sqLiteDatabase.close();
    }

    /**
     * 删除一条
     */
    public void deleteFor(String word) {
        if (TextUtils.isEmpty(word))
            return;
        SQLiteDatabase database = getDataBase().getWritableDatabase();
        database.execSQL("delete from words where word = ?", new Object[]{word});
        database.close();
    }

    /**
     * 修改一条
     */
    public void updateWord(WordBean wordBean) {
        if (wordBean == null || TextUtils.isEmpty(wordBean.word))
            return;
        SQLiteDatabase database = getDataBase().getWritableDatabase();
        Object[] objects = {wordBean.word, wordBean.translate, wordBean.isCollect, wordBean.word};
        database.execSQL("update words set word=?,translate=?,isCollect=? where word=?", objects);
        database.close();
    }

    /**
     * 查找 收藏的
     */
    public List<WordBean> queryWordsCollect() {
        ArrayList<WordBean> list = new ArrayList<>();
        SQLiteDatabase database = getDataBase().getReadableDatabase();
        try {
            Cursor cursor = database.rawQuery("select word from words where isCollect=?", new String[]{"1"});
            while (cursor.moveToNext()) {
                String word = cursor.getString(cursor.getColumnIndex("word"));
                String translate = cursor.getString(cursor.getColumnIndex("translate"));
                String isCollect = cursor.getString(cursor.getColumnIndex("isCollect"));
                WordBean wordBean = new WordBean(word, translate, "1".equals(isCollect));
                list.add(wordBean);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        database.close();
        return list;
    }

    /**
     * 查找数据库返回集合
     */
    public List<WordBean> queryWords() {
        ArrayList<WordBean> words = new ArrayList<>();
        SQLiteDatabase database = getDataBase().getWritableDatabase();
        try {
            Cursor cursor = database.query("words", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String word = cursor.getString(cursor.getColumnIndex("word"));
                String translate = cursor.getString(cursor.getColumnIndex("translate"));
                // boolean 存到数据库是 0 和 1
                String isCollect = cursor.getString(cursor.getColumnIndex("isCollect"));
                WordBean wordBean = new WordBean(word, translate, "1".equals(isCollect));

                words.add(wordBean);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        database.close();
        return words;
    }

}
