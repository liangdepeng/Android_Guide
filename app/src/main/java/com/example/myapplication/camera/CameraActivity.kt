package com.example.myapplication.camera

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.Toast
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityCamreaBinding

class CameraActivity : BaseActivity() {

    private val binding by lazy {
        ActivityCamreaBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.gotoCameraBtn.setOnClickListener {
            val intent = Intent().apply {
                action = MediaStore.ACTION_IMAGE_CAPTURE
            }
            startActivityForResult(intent,2323)
            Toast.makeText(this,"相机将在 30s 后自动关闭",Toast.LENGTH_SHORT).show()

            mainHandler.postDelayed({
                startActivity(Intent(this,CameraActivity::class.java))
            },30000)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== RESULT_OK){
            if (requestCode==2323){
                val bitmap = data?.extras?.get("data") as Bitmap

                binding.cameraIv.setImageBitmap(bitmap)
            }
        }
    }
}