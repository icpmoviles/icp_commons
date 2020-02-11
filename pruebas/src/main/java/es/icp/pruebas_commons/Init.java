package es.icp.pruebas_commons;

import android.app.Application;

import es.icp.pruebas_commons.helpers.GlobalVariables;

public class Init extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GlobalVariables.COLOR_APP = getApplicationContext().getColor(R.color.colorAccent);
        GlobalVariables.ICONO_APP = getApplicationContext().getDrawable(R.drawable.ic_launcher_round);
    }
}
