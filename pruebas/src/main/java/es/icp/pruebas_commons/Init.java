package es.icp.pruebas_commons;

import android.app.Application;

import com.android.volley.VolleyError;

import es.icp.icp_commons.CheckRequest;
import es.icp.icp_commons.Interfaces.NewVolleyCallBack;
import es.icp.icp_commons.Objects.CheckRequestException;
import es.icp.icp_commons.Objects.ParametrosPeticion;
import es.icp.icp_commons.Services.GeoTracking;
import es.icp.logs.core.MyLog;
import es.icp.pruebas_commons.helpers.GlobalVariables;
import es.icp.pruebas_commons.helpers.PruebasLoginRequest;
import es.icp.pruebas_commons.helpers.PruebasLoginResult;

public class Init extends Application {

    public static GeoTracking geoTracking;

    @Override
    public void onCreate() {
        super.onCreate();

        GlobalVariables.COLOR_APP = getApplicationContext().getColor(R.color.colorAccent);
        GlobalVariables.ICONO_APP = getApplicationContext().getDrawable(R.drawable.ic_launcher_round);

//        pruebaConApplicationContext();

        startGeoTrackingSystem();
    }

    private void startGeoTrackingSystem() {
        geoTracking = GeoTracking.getInstance(getApplicationContext());
        geoTracking.startGeoTracking();
    }

    private void pruebaConApplicationContext() {
        PruebasLoginRequest loginRequest = new PruebasLoginRequest("Pruebas", "p", "2", "28", "SAMSUNG", "A20e", "12121", "12312313", "cLF5wFYRxZE:APA91bFJHOM1MLBDunVBvzVip-MeLkDbfroplfELJSsCxUcX6pvNdwL_vtHiJdNjiQyXLH9zaTjHi3Lmcs-XmvAPtAcWAYbmfWXxP8kI1I4iD-rJKsbMIWasMTq_ocNFHqB_zMrtVsuh", "1.0.136");
        try {
            CheckRequest.CheckAndSend(
                    getApplicationContext(),
                    new ParametrosPeticion(ParametrosPeticion.Method.POST, "http://integracion.icp.es/WS_Orange_RFID_DES/api/orange.rfid/Login", loginRequest, PruebasLoginResult.class),
                    new NewVolleyCallBack() {
                        @Override
                        public void onSuccess(Object result) {
                            MyLog.d(result);
                        }

                        @Override
                        public void onError(VolleyError error) {
                            MyLog.d(error);
                        }

                        @Override
                        public void onOffline() {

                        }
                    },
                    0,
                    "http://integracion.icp.es/WS_Orange_RFID_DES/api/orange.rfid/save_log",
                    true
            );
        } catch (CheckRequestException ex) {
            ex.printStackTrace();
        }
    }
}
