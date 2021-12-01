package cn.example.common_module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/29
 * <p>
 * Summary: 利用 HandlerThread 构建 toast 显示队列
 */
public class ToastUtil {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static Handler toastHandler;

    public static void init(Context context) {
        mContext = context;
        HandlerThread toastThread = new HandlerThread("toast_thread");
        toastThread.start();
        toastHandler = new Handler(toastThread.getLooper());

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
//            Class<? extends Toast> toastClass = mToast.getClass();
//            try{
//                @SuppressLint("SoonBlockedPrivateApi")
//                Field mNextViewF = toastClass.getDeclaredField("mNextView");
//                mNextViewF.setAccessible(true);
//                ViewGroup viewGroup = (ViewGroup) ((View) mNextViewF.get(mToast));
//                TextView textView = (TextView) viewGroup.getChildAt(0);
//                textView.setTextColor(Color.RED);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }

    public static void show(String message) {
        toastHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast mToast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
                mToast.setGravity(Gravity.CENTER, 0, 0);
                mToast.show();
            }
        });
    }
}
