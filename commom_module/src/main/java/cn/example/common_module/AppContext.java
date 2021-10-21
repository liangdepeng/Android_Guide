package cn.example.common_module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/9/8
 * <p>
 * Summary: 全局获取 application Context
 */
public class AppContext {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static void init(Context mContext) {
        context = mContext;
    }

    public static Context getAppContext() {
        return context;
    }


    public final static Handler HANDLER = new Handler(Looper.getMainLooper());

    public static Toast getGlobalToast() {
        return Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }
}
