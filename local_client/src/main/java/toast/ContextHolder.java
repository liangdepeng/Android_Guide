package toast;

import android.app.Application;
import android.content.Context;

public class ContextHolder {

    private Context appContext;

    public static ContextHolder getInstance() {
        return Instance.INSTANCE;
    }

    public void init(Context context) {
        appContext = context;
    }

    public Context getAppContext() {
        if (appContext == null) {

            try {
                Application application = (Application) Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication").invoke(null, (Object[]) null);
                if (application != null) {
                    appContext = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Application application = (Application) Class.forName("android.app.AppGlobals")
                        .getMethod("getInitialApplication").invoke(null, (Object[]) null);
                if (application != null) {
                    appContext = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            throw new IllegalStateException("ContextHolder is not initialed, it is recommend to init with application context.");
        }

        return appContext;
    }

    private static class Instance {
        public final static ContextHolder INSTANCE = new ContextHolder();
    }
}
