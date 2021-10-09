package com.example.moudle_study

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.example.common_module.IPlayService
import cn.example.common_module.JumpServiceFactory

class StudyTestMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_test_main)

        findViewById<TextView>(R.id.click_tv).setOnClickListener {
            val iPlayService = JumpServiceFactory.INSTANCE.getComponentService(
                IPlayService::class.java.name) as IPlayService
            iPlayService.launchPlayTest(this, Bundle())
        }
    }
}