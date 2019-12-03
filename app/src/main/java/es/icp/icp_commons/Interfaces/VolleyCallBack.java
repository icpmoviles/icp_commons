package es.icp.icp_commons.Interfaces;

import es.icp.icp_commons.Objects.VolleyError;

public interface VolleyCallBack {

    void onSuccess (Object result);
    void onError (String error);
    void onOffline();

}
