package es.icp.icp_commons.CommonsCore;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

public class CommonsExecutors {
    private static Executor executorIO;
    private static Executor executorWEB;
    private static Executor mainThread;

    private static CommonsExecutors INSTANCIA;

    private CommonsExecutors(Executor executorIO, Executor executorWEB, Executor mainThread) {
        CommonsExecutors.executorIO  = executorIO;
        CommonsExecutors.executorWEB = executorWEB;
        CommonsExecutors.mainThread  = mainThread;
    }

    public CommonsExecutors() {
        new CommonsExecutors(java.util.concurrent.Executors.newSingleThreadExecutor(), java.util.concurrent.Executors.newFixedThreadPool(3), new MainThreadExecutor());
    }

    public Executor Sec() {
        return executorIO;
    }

    public Executor WEB() {
        return executorWEB;
    }

    public Executor Main() {
        return mainThread;
    }

    public static CommonsExecutors getExecutor() {
        if (INSTANCIA == null) {
            CommonsExecutors executors = new CommonsExecutors(java.util.concurrent.Executors.newSingleThreadExecutor(), java.util.concurrent.Executors.newFixedThreadPool(3), new MainThreadExecutor());
            INSTANCIA = executors;
        }
        return INSTANCIA;
    }

    private static class MainThreadExecutor implements Executor {

        private Handler mainThreadHandler = new Handler(Looper.myLooper());

        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
