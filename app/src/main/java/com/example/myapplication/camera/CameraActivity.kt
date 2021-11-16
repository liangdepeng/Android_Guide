package com.example.myapplication.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.util.Printer
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.common_module.dpToPx
import com.example.common_module.simpleToast
import com.example.common_module.snackToast
import com.example.common_module.spToPx
import com.example.down_module.util.FileUtil
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityCamreaBinding
import java.io.File

class CameraActivity : BaseActivity() {

    private val binding by lazy {
        ActivityCamreaBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.gotoCameraBtn.setOnClickListener {

            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_DENIED
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 123321)
                return@setOnClickListener
            }

            startToCamera()
        }
    }

    private fun startToCamera() {
        val intent = Intent().apply {
            action = MediaStore.ACTION_IMAGE_CAPTURE
        }
        startActivityForResult(intent, 2323)
        Toast.makeText(this, "相机将在 30s 后自动关闭", Toast.LENGTH_SHORT).show()

        mainHandler.postDelayed({
            startActivity(Intent(this, CameraActivity::class.java))
        }, 30000)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEach {
            if (it != PackageManager.PERMISSION_GRANTED) {
                "请先授予相机权限".simpleToast()
                return
            }
        }

        startToCamera()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 2323) {
                var bitmap = data?.extras?.get("data") as Bitmap

//                var scaleBitmap = Bitmap.createScaledBitmap(bitmap,
//                    bitmap.width / 1, bitmap.height / 1, true)

                val fileUtil = FileUtil(Environment.DIRECTORY_PICTURES)
                var file = fileUtil.createFile("${System.currentTimeMillis()}zzxxjj.png")
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, file.outputStream())

                // todo  方法无效待修改
                val degree = ImageUtil.readPictureDegree(file.path)
                if (degree != 0) {
                    bitmap = ImageUtil.rotaingImageView(degree, bitmap)
                    file = fileUtil.createFile("${System.currentTimeMillis()}zzxxjjkk.png")
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, file.outputStream())
                }

                if (Build.VERSION.SDK_INT >= 29) {
                    fileUtil.copyFileToSdCardForAndroidQ(file)
                }

                binding.cameraIv.setImageBitmap(bitmap)
            }
        }
    }

    private fun testKTexpandUtil(){
        isMainThread
        dpToPx(12f)
        spToPx(12f)

        binding.cameraIv.dpToPx(12f)

        "sim".simpleToast()
        "simsmi".snackToast(this)

    }
}