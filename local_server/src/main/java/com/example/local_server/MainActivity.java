package com.example.local_server;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.local_server.service.AidlService;

public class MainActivity extends AppCompatActivity {

    private IMyAidlInterface anInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bindTv = findViewById(R.id.service_tv);
        bindTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBind) {
                    unbindService(connection);
                    isBind = false;
                    bindTv.setText("绑定服务");
                } else {
                    Intent intent = new Intent(MainActivity.this, AidlService.class);
                    boolean bindService = bindService(intent, connection, BIND_AUTO_CREATE);
                    bindTv.setText(bindService + "  解除绑定");
                }
            }
        });

        findViewById(R.id.send_message_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                    try {
                        anInterface.setMsg("本地消息");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        });

        findViewById(R.id.get_message_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(MainActivity.this,anInterface.getServiceMsg(),Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean isBind = false;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBind = true;
            anInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
            anInterface = null;
        }
    };
}