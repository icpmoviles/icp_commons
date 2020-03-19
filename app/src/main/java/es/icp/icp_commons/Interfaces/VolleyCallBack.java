package es.icp.icp_commons.Interfaces;

/**
 * @deprecated Usar la interfaz NewVolleyCallBack
 */
@Deprecated
public interface VolleyCallBack {

    void onSuccess(Object result);

    void onError(String error);

    void onOffline();

}
