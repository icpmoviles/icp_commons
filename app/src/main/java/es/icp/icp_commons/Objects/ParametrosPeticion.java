package es.icp.icp_commons.Objects;

import com.android.volley.Request;

import org.json.JSONObject;

public class ParametrosPeticion {
    private int method;
    private String url;
    private JSONObject json;

    public ParametrosPeticion() {
    }

    public ParametrosPeticion(int method, String url, JSONObject json) {
        this.method = method;
        this.url = url;
        this.json = json;
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

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    @Override
    public String toString() {
        return "ParametrosPeticion{" +
                "method=" + method +
                ", url='" + url + '\'' +
                ", json=" + json +
                '}';
    }
}
