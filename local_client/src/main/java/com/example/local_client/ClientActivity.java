package com.example.local_client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.local_server.IAidlCallback;
import com.example.local_server.IMyAidlInterface;

import abc.common.util.KtExpandUtil;


/**
 * Created by ldp.
 * <p>
 * Date: 2021/6/23
 * <p>
 * Summary:
 */
public class ClientActivity extends AppCompatActivity{

    private IMyAidlInterface iMyAidlInterface;
    private boolean isBind = false;
    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBind = true;
            Log.e("client", "client - onServiceConnected " + name);
            //得到一个远程Service中的Binder代理，而不是该Binder实例
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);

            try {

                aidlCallback = new IAidlCallback.Stub() {
                    @Override
                    public void testCallback(String msg) throws RemoteException {
//                        ToastUtil.show(msg);
                        KtExpandUtil.Companion.showToast(msg);
                    }

                    @Override
                    public boolean testCallback2() throws RemoteException {
                        return false;
                    }

                };

                 //注册回调
                iMyAidlInterface.setCallback(aidlCallback);

                iMyAidlInterface.setMsg("connected success");

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        /**
         * //该回调方法一般不会调用，如果在解绑的时候，发现该方法没有调用，不要惊慌，
         * 因为该方法的调用时机是Service被意外销毁时，比如内存不足时。
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("client", "client - onServiceDisconnected " + name);
            isBind = false;
            iMyAidlInterface = null;
        }
    };
    private TextView msgTv;
    private IAidlCallback aidlCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_test);
        EditText editText = findViewById(R.id.edit_text);
        Button sendMsgBtn = findViewById(R.id.send_msg_btn);
        Button bindBtn = findViewById(R.id.bind_btn);
        Button getMsgBtn = findViewById(R.id.get_msg_btn);
        msgTv = findViewById(R.id.msg_tv);

//        Intent intent = new Intent();
//        intent.setAction("com.process.example.server.serveraidl.IMyAidlInterface");
//        intent.setPackage("com.process.example.server");
//        ComponentName componentName = startService(intent);
//        ToastUtil.show(componentName.toString());

        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String in = editText.getText().toString().trim();
                if (TextUtils.isEmpty(in)) {
                    //ToastUtil.show("不能为空");
                    KtExpandUtil.Companion.showToast("不能为空");
                    return;
                }
                try {
                    iMyAidlInterface.setMsg(in);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        bindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBind) {
                    try {
                   //     iMyAidlInterface.removeCallback(aidlCallback);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //解除绑定，当调用unbindService时，一定要判断当前service是否是binded的，如果没有，就会报错。
                    // 解绑 服务即销毁
                    unbindService(connection);
                    iMyAidlInterface = null;
                    isBind = false;
                    bindBtn.setText("绑定服务");
                } else {

                    //Android5.0及以后，出于对安全的考虑，Android系统对隐式启动Service做了限制，
                    // 需要带上包名或者类名，这一点需要注意。

                    ComponentName componentName = new ComponentName("com.example.local_server", "com.example.local_server.service.AidlService");
                    Intent intent = new Intent();
                    intent.setComponent(componentName);
                    boolean bindService = bindService(intent, connection, BIND_AUTO_CREATE);
                    bindBtn.setText(bindService + "  解绑服务");
                }
            }
        });

        getMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String serverMessage = iMyAidlInterface.getServiceMsg();
                    msgTv.setText(serverMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
