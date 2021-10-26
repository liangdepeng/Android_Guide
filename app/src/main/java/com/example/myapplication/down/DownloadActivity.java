package com.example.myapplication.down;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.down_module.MultiThreadManager;
import com.example.down_module.SingleThreadManager;
import com.example.myapplication.databinding.ActivityDownloadBinding;

public class DownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDownloadBinding binding = ActivityDownloadBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        requestPermission();

        SingleThreadManager downloadManager = new SingleThreadManager();

        binding.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.inEt.getText().toString()))
                    Toast.makeText(DownloadActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                downloadManager.downloadObj(binding.inEt.getText().toString(),null);
            }
        });

        MultiThreadManager multiThreadManager = new MultiThreadManager();

        binding.downloadMutliBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.inEt2.getText().toString()))
                    Toast.makeText(DownloadActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                multiThreadManager.downObj(binding.inEt2.getText().toString(),null);
            }
        });
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}