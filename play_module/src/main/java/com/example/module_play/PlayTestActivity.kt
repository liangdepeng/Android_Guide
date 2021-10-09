package com.example.module_play

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.example.common_module.IStudyService
import cn.example.common_module.JumpServiceFactory

class PlayTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_test)

        findViewById<TextView>(R.id.play_tv).setOnClickListener {
            // JumpServiceFactory.INSTANCE.studyService.launch(this, Bundle())

            // playmoudle 跳转 studymoudle 互不依赖 组件通信
            val iStudyService =
                JumpServiceFactory.INSTANCE.getComponentService<IStudyService>(
                    IStudyService::class.java.name)
            iStudyService?.launchStudyTest(this, Bundle())
        }

    }
}