package es.icp.icp_commons.Interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface VolleyCallBack {

    void onSuccess (JSONObject result);
    void onError (VolleyError error);
    void onOffline();

}
