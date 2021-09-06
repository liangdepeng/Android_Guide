package toast;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;


/**
 *  @date 2020/8/26
 * 部分7.1.1手机崩溃Toast解决方案
 * Toast显示与隐藏
 * <p>
 * 首先Toast显示依赖于一个窗口，这个窗口被WMS管理（WindowManagerService），当需要show的时候这个请求会放在WMS请求队列中，
 * 并且会传递一个TN类型的Bider对象给WMS，WMS并生成一个token传递给Android进行显示与隐藏，但是如果UI线程的某个线程发生了阻塞，
 * 并且已经NotificationManager检测已经超时就不删除token记录，此时token已经过期，阻塞结束的时候再显示的时候就发生了异常。
 * <p>
 * 在android7.1.1的Toast源码handleShow是这样写的:
 * <p>
 * mWM.addView(mView, mParams);
 * <p>
 * 在android 8.0 是这么写的
 * try {
 * mWM.addView(mView, mParams);
 * trySendAccessibilityEvent();
 * } catch (WindowManager.BadTokenException e) {
 * //   ignore
 * }
 * <p>
 * 通过反射注入在发生异常的地方进行try catch
 */
public class ToastWrapper {

    private static Field sFiled_TN;
    private static Field sFiled_TN_Handler;
    private static Toast mToast;

    public static void show(Context context, CharSequence text, int showLength, int gravity) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, showLength);
            mToast.setGravity(gravity,0,0);
        } else {
            mToast.setText(text);
        }

        hook(mToast);
        mToast.show();
    }

    static {
        try {
            sFiled_TN = Toast.class.getDeclaredField("mTN");
            sFiled_TN.setAccessible(true);
            sFiled_TN_Handler = sFiled_TN.getType().getDeclaredField("mHandler");
            sFiled_TN_Handler.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void hook(Toast toast) {
        try {
            Object tn = sFiled_TN.get(toast);
            Handler handler = (Handler) sFiled_TN_Handler.get(tn);
            sFiled_TN_Handler.set(tn, new SafeHandlerWrapper(handler));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static class SafeHandlerWrapper extends Handler {

        private final Handler handler;

        public SafeHandlerWrapper(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void dispatchMessage(@NonNull Message msg) {
            try {
                super.dispatchMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            try {
                handler.handleMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
