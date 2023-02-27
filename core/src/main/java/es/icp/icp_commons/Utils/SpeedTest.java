package es.icp.icp_commons.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import es.icp.icp_commons.CustomNotification;
import es.icp.icp_commons.Helpers.MyApplication;
import es.icp.logs.core.MyLog;

/**
 * Me he basado en esto http://www.gregbugaj.com/?attachment_id=70
 */

public class SpeedTest {

    private static SpeedTest INSTANCE;

    private Context context;
    private boolean loading;

    private SpeedTest(Context context) {
        this.context     = context;
        mDecimalFormater = new DecimalFormat("##.##");
    }

    public static SpeedTest getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SpeedTest(context);
        }
        return INSTANCE;
    }

    public void startTest(boolean loading) {
        this.loading = loading;
        SpeedTestTask speedTestTask = new SpeedTestTask();
        speedTestTask.execute();
    }

    public class SpeedTestTask extends AsyncTask<Boolean, String, SpeedInfo> {

        private CustomNotification customNotification;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            MyApplication.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    customNotification = new CustomNotification.Builder(context)
                            .setProgressMode()
                            .setMax(100)
                            .setProgress(0)
                            .setMinimizable(false)
                            .setText("Inicializando el test de velocidad...")
                            .aboveDarkness(false)
                            .setDuration(CustomNotification.LENGTH_LONG)
                            .build();

                    customNotification.show();
                }
            });

        }

        @Override
        protected SpeedInfo doInBackground(Boolean... booleans) {
            InputStream stream = null;
            try {
                int           bytesIn         = 0;
                String        downloadFileUrl = "https://www.lipsum.com/";
                long          startCon        = System.currentTimeMillis();
                URL           url             = new URL(downloadFileUrl);
                URLConnection con             = url.openConnection();
                con.setUseCaches(false);
                long connectionLatency = System.currentTimeMillis() - startCon;
                stream = con.getInputStream();

                MyApplication.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "latencia: " + connectionLatency, Toast.LENGTH_SHORT).show();
                    }
                });

                long start            = System.currentTimeMillis();
                int  currentByte      = 0;
                long updateStart      = System.currentTimeMillis();
                long updateDelta      = 0;
                int  bytesInThreshold = 0;

                while ((currentByte = stream.read()) != -1) {
                    bytesIn++;
                    bytesInThreshold++;
                    if (updateDelta >= UPDATE_THRESHOLD) {
                        int       progress  = (int) ((bytesIn / (double) EXPECTED_SIZE_IN_BYTES) * 100);
                        SpeedInfo speedInfo = calculate(updateDelta, bytesInThreshold);

                        if (loading) {
                            publishProgress("Progreso: " + progress + "% -> Velocidad: " + mDecimalFormater.format(speedInfo.kilobits) + " KB/S", String.valueOf(progress));
                        }

                        //Reset
                        updateStart      = System.currentTimeMillis();
                        bytesInThreshold = 0;
                    }
                    updateDelta = System.currentTimeMillis() - updateStart;
                }

                long downloadTime = (System.currentTimeMillis() - start);

                if (downloadTime == 0) {
                    downloadTime = 1;
                }

                SpeedInfo speedInfoFinal = calculate(downloadTime, bytesIn);

                MyApplication.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Resultado del test: " + mDecimalFormater.format(speedInfoFinal.kilobits) + " KB/S", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stream != null) {
                        stream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(SpeedInfo speedInfo) {
            super.onPostExecute(speedInfo);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            MyApplication.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    customNotification.showText(values[0]);
                    customNotification.setProgress(Integer.parseInt(values[1]));
                    MyLog.d("CALCULANDO... " + values[0]);
                }
            });

        }

    }

    private SpeedInfo calculate(final long downloadTime, final long bytesIn) {
        SpeedInfo info = new SpeedInfo();
        //from mil to sec
        long   bytespersecond = (bytesIn / downloadTime) * 1000;
        double kilobits       = bytespersecond * BYTE_TO_KILOBIT;
        double megabits       = kilobits * KILOBIT_TO_MEGABIT;
        info.downspeed = bytespersecond;
        info.kilobits  = kilobits;
        info.megabits  = megabits;

        return info;
    }

    private static class SpeedInfo {
        public double kilobits  = 0;
        public double megabits  = 0;
        public double downspeed = 0;
    }

    private static final int EXPECTED_SIZE_IN_BYTES = 1048576;//1MB 1024*1024

    private static final double BYTE_TO_KILOBIT    = 0.0078125;
    private static final double KILOBIT_TO_MEGABIT = 0.0009765625;

    private final static int UPDATE_THRESHOLD = 300;


    private DecimalFormat mDecimalFormater;

}