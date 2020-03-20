package es.icp.icp_commons.Helpers;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;


public class MyApplication extends Application {
    private static final Handler handler = new Handler();

    public static final void runOnUiThread(Runnable runnable) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runnable.run();
        } else {
            handler.post(runnable);
        }
    }
}