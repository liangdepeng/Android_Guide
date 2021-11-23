package com.ldp.jetpack_app.jetpack.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore

// 创建 Preferences DataStore
//使用由 preferencesDataStore 创建的属性委托来创建 Datastore<Preferences> 实例。
// 在您的 Kotlin 文件顶层调用该实例一次，便可在应用的所有其余部分通过此属性访问该实例。
// 这样可以更轻松地将 DataStore 保留为单例。此外，如果您使用的是 RxJava，请使用 RxPreferenceDataStoreBuilder。
// 必需的 name 参数是 Preferences DataStore 的名称。
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

// 另一种  Proto DataStore
// https://blog.csdn.net/CQ0911/article/details/115467679

/**
 * Created by ldp.
 * Date: 2021/11/23
 * Summary:  DataStore
 */
class DataStoreUtil {

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        fun init(mContext: Context) {
            context = mContext
        }

//        val EXAMPLE_COUNTER = intPreferencesKey("example_counter")
//        val exampleCounterFlow: Flow<Int> = context.dataStore.data.map { preferences ->
//            // No type safety.
//            preferences[EXAMPLE_COUNTER] ?: 0
//        }

        /**
         * ---------使用 Preferences DataStore 存储键值对-----------
         */
        suspend fun <T> asyncSave(key: Preferences.Key<T>, value: T) {
            context.dataStore.edit {
                it.set(key = key, value = value)
            }
        }

        suspend fun <T> asyncGet(key: Preferences.Key<T>): T? {
            var value: T? = null
            context.dataStore.edit {
                value = it.get(key = key) as T?
            }
            return value
        }
        /**
         * ---------------------------------------------------------
         */



    }

}