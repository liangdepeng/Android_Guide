package com.example.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.thread.ExecutorHelper;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/16
 * <p>
 * Summary: 服务 运行在 主线程 主线程 主线程
 */
public class MyService extends Service {

    private MyHandler myHandler;

    private MyBinder myBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        myHandler = new MyHandler(this);
        myBinder = new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myHandler.removeMessages(200);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Message message = myHandler.obtainMessage();
                    message.what = 200;
                    message.obj = i + " " + this.toString() + " " + Thread.currentThread().toString() + " ";
                    myHandler.sendMessage(message);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        ExecutorHelper.getExec().execute(runnable);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // startService()  return null;
        // bindService()  return myBinder;

        return myBinder;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacksAndMessages(null);
    }

    static class MyHandler extends Handler {

        private final WeakReference<MyService> reference;

        public MyHandler(MyService service) {
            super(Looper.getMainLooper());
            reference = new WeakReference<>(service);
        }

        @Override
        public void dispatchMessage(@NonNull Message msg) {
//            super.dispatchMessage(msg);

            if (msg.what == 200 && reference.get() != null) {
                Toast.makeText(reference.get(), " 来自service的消息 " + msg.obj, Toast.LENGTH_SHORT).show();
            }

        }
    }

    /**
     * 如果 调用 bindService 需要实现 Binder  onBind 返回
     */
    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    private final Random random = new Random();

    public String getContent(){
        return random.nextInt(1000)+" "+toString()+" "+Thread.currentThread().toString();
    }
}
