package com.example.local_server.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.local_server.IAidlCallback;
import com.example.local_server.IMyAidlInterface;
import com.example.local_server.IUserObj;

import java.util.Random;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/3
 * <p>
 * Summary: 进程通信 远程服务端
 */
public class AidlService extends Service {

    private final Handler handler = new Handler(Looper.getMainLooper());

    public AidlService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        showMsg(this,"远程服务绑定成功");
        return new MyIBinder(this);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        showMsg(this,"远程服务解绑成功");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    static class MyIBinder extends IMyAidlInterface.Stub{

        private AidlService aidlService;

        public MyIBinder(AidlService aidlService) {
            this.aidlService=aidlService;
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void setMsg(String message) throws RemoteException {

            aidlService.showMsg(aidlService,"服务端收到客户端的消息 ："+message);

            aidlService.handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    aidlService.callback("来自服务端的回执消息");
                }
            },3000);
        }

        @Override
        public String getServiceMsg() throws RemoteException {
            return "服务端的消息————随机数字---"+(new Random().nextInt(1000));
        }

        @Override
        public void setUserObj(IUserObj obj) throws RemoteException {

        }

        @Override
        public IUserObj getUserObj() throws RemoteException {
            return null;
        }

        @Override
        public void setCallback(IAidlCallback callback) throws RemoteException {
            if (callback!=null){
                aidlService.mCallbacks.register(callback);
            }
        }

        @Override
        public void removeCallback(IAidlCallback callback) throws RemoteException {
            if (callback!=null){
                aidlService.mCallbacks.unregister(callback);
            }
        }
    }

    private final RemoteCallbackList<IAidlCallback> mCallbacks = new RemoteCallbackList<>();

    private void callback(String msg) {

        int count = mCallbacks.beginBroadcast();
        for (int i = 0; i < count; i++) {
            try {
                mCallbacks.getBroadcastItem(i).testCallback(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mCallbacks.finishBroadcast();
    }


    private void showMsg(AidlService context, String msg) {
        if (Looper.myLooper()==null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
            });
        }else {
            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
}
