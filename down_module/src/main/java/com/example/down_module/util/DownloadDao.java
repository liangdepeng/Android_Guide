package com.example.down_module.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.down_module.DownloadDBHelper;

import java.util.HashMap;
import java.util.Map;

import cn.example.common_module.AppContext;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/10/26
 * <p>
 * Summary:
 */
public class DownloadDao {

    private final DownloadDBHelper dbHelper;

    public DownloadDao() {
        dbHelper = new DownloadDBHelper(AppContext.getAppContext());
    }

    /**
     * 获取历史下载数据
     *
     * @param url
     * @return
     */
    public synchronized HashMap<Integer, Integer> getDownloadProgressData(String url) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("select threadid,downlength from filedownlog where downpath = ?", new String[]{url});
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            //把线程id与该线程已下载的长度存放到data哈希表中
            hashMap.put(cursor.getInt(cursor.getColumnIndex("threadid")), cursor.getInt(cursor.getColumnIndex("downlength")));
        }

        cursor.close();
        database.close();
        return hashMap;
    }

    /**
     * 保存每条线程已经下载的文件长度
     *
     * @param path    下载的路径
     * @param hashMap 现在的id和已经下载的长度的集合
     */
    public synchronized void save(String path, Map<Integer, Integer> hashMap) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        // 开启事务，插入多条数据
        database.beginTransaction();
        try {
            for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
                //插入特定下载路径特定线程ID已经下载的数据
                database.execSQL("insert into filesownlog(downpath,threadid,downlength) values(?,?,?)", new Object[]{path, entry.getKey(), entry.getValue()});
            }
            //设置一个事务成功的标志,如果成功就提交事务,如果没调用该方法的话那么事务回滚
            //就是上面的数据库操作撤销
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            database.close();
        }
    }

    /**
     * 实时更新线程下载的进度
     */
    public synchronized void update(String path, int threadId, int progress) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("update filedownlog set downlength=? where downpath=? and threadid=?", new Object[]{progress, path, threadId});
        database.close();
    }

    /**
     * 下载完成 删除记录
     */
    public synchronized void delete(String path) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.execSQL("delete from filedownlog where downpath=?", new Object[]{path});
        database.close();
    }
}
