package com.ldp.jetpack_app.jetpack.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.ldp.jetpack_app.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Room 的简单使用
 */
class RoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val handler = Handler(Looper.getMainLooper())

        Thread{

            val users = DatabaseUtil.instance.getAll()

            handler.post {
                Toast.makeText(this@RoomActivity,"${users.size}  已保存",Toast.LENGTH_SHORT).show()
            }

            DatabaseUtil.instance.insertUsers(User(System.currentTimeMillis().hashCode(),"123213",122,"女"))

            Thread.sleep(1000)

            val all = DatabaseUtil.instance.getAll()
            all.size

            Thread.sleep(1000)

            DatabaseUtil.instance.delete(User(1,"张三 ${System.currentTimeMillis()}",12,"男"))

            Thread.sleep(1000)

            val all1 = DatabaseUtil.instance.getAll()
            all1.size


        }.start()

    }
}