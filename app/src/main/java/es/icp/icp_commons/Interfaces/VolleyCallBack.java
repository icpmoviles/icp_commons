package es.icp.icp_commons.Interfaces;

import es.icp.icp_commons.Objects.VolleyError;

public interface VolleyCallBack {

    void onSuccess (Object result);
    void onError (VolleyError error);
    void onOffline();

}
