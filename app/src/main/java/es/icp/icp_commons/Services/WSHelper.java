package es.icp.icp_commons.Services;

import es.icp.logs.core.MyLog;


public class WSHelper {


    /**
     * Muetra info de la URL llamada al WS
     *
     * @param url
     */
    public static void logWS(String url) {
        logWS(url, null);
    }

    /**
     * Muestra informacion de la llamada realizada, mostrando la url y los parametros json enviados.
     *
     * @param url
     * @param jsonObject
     */
    public static void logWS(String url, Object jsonObject) {
        if (!Boolean.parseBoolean("true")) return;

        MyLog.setDEBUG(true);
        MyLog.d("");
        MyLog.l();
        MyLog.f(url);
        MyLog.d("Llamada desde :" + MyLog.getTrace(4));
        if (jsonObject != null) MyLog.d("JSON:" + jsonObject.toString());
        MyLog.l();
        MyLog.d("");

    }


}
