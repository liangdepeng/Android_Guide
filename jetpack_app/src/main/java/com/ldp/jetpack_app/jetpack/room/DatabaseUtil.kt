package com.ldp.jetpack_app.jetpack.room

import androidx.room.Room
import com.ldp.jetpack_app.MyApplication

/**
 * Created by ldp.
 *
 * Date: 2021/11/23
 *
 * Summary: 数据库实例单例
 */
object DatabaseUtil {

    val instance by lazy {
        Room.databaseBuilder(MyApplication.context, AppDatabase::class.java, "user_database")
            .build().userDao()
    }

    fun initDataBase() {
        Thread {
            if (instance.getAll().isNotEmpty()) return@Thread
            instance.insertUsers(User(1, "张三 ${System.currentTimeMillis()}", 12, "男"))
            instance.insertUsers(User(2, "李四 ${System.currentTimeMillis()}", 22, "男"))
            instance.insertUsers(User(3, "王五 ${System.currentTimeMillis()}", 14, "男"))
            instance.insertUsers(User(4, "赵六 ${System.currentTimeMillis()}", 45, "男"))
            instance.insertUsers(User(5, "恒七 ${System.currentTimeMillis()}", 23, "男"))
        }.start()
    }

}