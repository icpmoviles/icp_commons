package es.icp.icp_commons.Helpers;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;


public class MyApplication extends Application {
    private static final Handler handler = new Handler(Looper.getMainLooper());

    public static final void runOnUiThread(Runnable runnable) {
        Looper looper = Looper.getMainLooper();
        if (Thread.currentThread() == looper.getThread()) {
            runnable.run();
        } else {
            handler.post(runnable);
        }
    }
}