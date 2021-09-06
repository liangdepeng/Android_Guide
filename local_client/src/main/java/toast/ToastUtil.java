package toast;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;


public class ToastUtil {

    private final static Handler HANDLER = new Handler(Looper.getMainLooper());

    public static void show(final CharSequence text) {
        show(text, Gravity.CENTER);
    }

    public static void show(final CharSequence text, final int gravity) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Toast toast = Toast.makeText(ContextHolder.getInstance().getAppContext(), text, Toast.LENGTH_SHORT);
                    toast.setGravity(gravity, 0, 0);
                    toast.show();
                } else {
                    ToastWrapper.show(ContextHolder.getInstance().getAppContext(), text, Toast.LENGTH_SHORT, gravity);
                }
            }
        });
    }
}
