package es.icp.icp_commons.Interfaces;

import com.android.volley.VolleyError;

public interface NewVolleyCallBack {

    void onSuccess(Object result);

    void onError(VolleyError error);

    void onOffline();

}
