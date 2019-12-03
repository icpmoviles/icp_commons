package es.icp.icp_commons.Objects;

import com.android.volley.Request;
import org.json.JSONObject;
import org.json.JSONArray;

import org.json.JSONObject;

public class ParametrosPeticion {
    private int method;
    private String url;
    private JSONObject JSONObject;
    private JSONArray JSONArray;
    private JsonTypes jsonType;
    public enum JsonTypes {
        SIMPLE,
        ARRAY,
        ERROR
    }

    public ParametrosPeticion() {
    }

    public ParametrosPeticion(int method, String url, JSONObject json) {
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
        return method;
    }

    public void setMethod(int method) {
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
