package com.ldp.jetpack_app.jetpack.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by ldp.
 *
 * Date: 2021/11/23
 *
 * Summary:
 */
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}