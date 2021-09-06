package com.example.module_play

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.commom_module.IStudyService
import com.example.commom_module.JumpServiceFactory

class PlayTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_test)

        findViewById<TextView>(R.id.play_tv).setOnClickListener {
            // JumpServiceFactory.INSTANCE.studyService.launch(this, Bundle())
            val iStudyService =
                JumpServiceFactory.INSTANCE.getComponentService<IStudyService>(IStudyService::class.java.name)
            iStudyService?.launchStudyTest(this, Bundle())
        }

    }
}