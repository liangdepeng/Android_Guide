package com.example.myapplication.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.base.BaseRecyclerAdapter;
import com.example.myapplication.base.BaseViewHolder;
import com.example.myapplication.databinding.ActivityServiceBinding;

import java.lang.ref.WeakReference;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/16
 * <p>
 * Summary: 服务 四大组件之一
 */
public class ServiceActivity extends AppCompatActivity {

    private ActivityServiceBinding binding;
    private MyHandler myHandler;
    private BaseRecyclerAdapter<String, BaseViewHolder> recyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServiceBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        myHandler = new MyHandler(this);

        binding.serviceLaunchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 开启一个服务 不能和服务端通信
                Intent intent = new Intent(ServiceActivity.this, MyService.class);
                startService(intent);
            }
        });

        binding.serviceBindBtn.setOnClickListener(new View.OnClickListener() {
            private boolean isBind = false;

            @Override
            public void onClick(View v) {
                if (isBind) {
                    unbindService(connection);
                    myHandler.removeMessages(200);
                    isBind = false;
                    binding.serviceBindBtn.setText("绑定服务");
                } else {
                    Intent intent = new Intent(ServiceActivity.this, MyService.class);
                    isBind = bindService(intent, connection, BIND_AUTO_CREATE);
                    if (isBind)binding.serviceBindBtn.setText("解除绑定服务");
                }
            }
        });

        initRV();
    }


    private MyService myService;

    // 调用 bindService 需要 创建 ServiceConnection 可以和服务端通信
    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(ServiceActivity.this, "onServiceConnected", Toast.LENGTH_SHORT).show();

            MyService.MyBinder myBinder = (MyService.MyBinder) service;
            myService = myBinder.getService();

            if (myService != null) {
                myHandler.sendEmptyMessageDelayed(200, 3000);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
        }
    };


    private void initRV() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        binding.recyclerview.setLayoutManager(manager);
        recyclerAdapter = new BaseRecyclerAdapter<String, BaseViewHolder>(this) {

            @NonNull
            @Override
            public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                TextView textView = new TextView(mContext);
                textView.setTextSize(14f);
                textView.setTextColor(Color.BLUE);
                textView.setPadding(30, 40, 30, 40);

                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                textView.setLayoutParams(layoutParams);

                return new BaseViewHolder(textView);
            }

            @Override
            protected void onBindItemView(BaseViewHolder holder, String s, int position) {
                ((TextView) holder.itemView).setText(position + ": " + s);
            }
        };
        binding.recyclerview.setAdapter(recyclerAdapter);
    }

    private static class MyHandler extends Handler {

        private final WeakReference<ServiceActivity> reference;

        public MyHandler(ServiceActivity activity) {
            super(Looper.getMainLooper());
            reference = new WeakReference<>(activity);
        }

        @Override
        public void dispatchMessage(@NonNull Message msg) {
//            super.dispatchMessage(msg);

            if (msg.what == 200) {

                if (reference.get() != null) {
                    reference.get().recyclerAdapter.addData(reference.get().myService.getContent());
                    reference.get().binding.recyclerview.smoothScrollToPosition(reference.get().recyclerAdapter.getLastIndex());
                }

                msg.getTarget().sendEmptyMessageDelayed(200, 3000);
            }

        }
    }
}
