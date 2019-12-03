package es.icp.icp_commons.Objects;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONObject;
import org.json.JSONArray;

import org.json.JSONObject;

public class ParametrosPeticion {
    private Method method;
    private String url;
    private JSONObject JSONObject;
    private JSONArray JSONArray;
    private JsonTypes jsonType;
    public enum Method {
        POST,
        GET
    }
    public enum JsonTypes {
        SIMPLE,
        ARRAY,
        ERROR
    }

    public ParametrosPeticion() {
        method = Method.GET;
    }

    public ParametrosPeticion(Method method, String url, JSONObject json) {
        this.method = method;
        this.url = url;
        JSONObject = json;
        jsonType = JsonTypes.SIMPLE;
//
//        if (json instanceof JSONArray) {
//            JSONArray = (JSONArray) json;
//            JSONObject = null;
//            jsonType = JsonTypes.ARRAY;
//        } else if (json instanceof JSONObject) {
//            JSONObject = (JSONObject) json;
//            JSONArray = null;
//            jsonType = JsonTypes.SIMPLE;
//        } else {
//            JSONObject = null;
//            JSONArray = null;
//            jsonType = JsonTypes.ERROR;
//        }
    }

    public int getMethod() {
        int m = 0;
        if (method == Method.POST) m = Request.Method.POST;
        if (method == Method.GET) m = Request.Method.GET;
        return m;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONObject getJSONObject() {
        return JSONObject;
    }

    public void setJSONObject(JSONObject JSONObject) {
        this.JSONObject = JSONObject;
        JSONArray = null;
        jsonType = JsonTypes.SIMPLE;
    }

    public JSONArray getJSONArray() {
        return JSONArray;
    }

    public void setJSONArray(JSONArray JSONArray) {
        this.JSONArray = JSONArray;
        JSONObject = null;
        jsonType = JsonTypes.ARRAY;
    }

    public JsonTypes getJsonType() {
        return jsonType;
    }

    @Override
    public String toString() {
        return "ParametrosPeticion{" +
                "method=" + method +
                ", url='" + url + '\'' +
                ", JSONObject=" + JSONObject +
                ", JSONArray=" + JSONArray +
                ", jsonType=" + jsonType +
                '}';
    }
}
