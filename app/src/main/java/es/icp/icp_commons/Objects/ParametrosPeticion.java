package es.icp.icp_commons.Objects;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings({"unused", "WeakerAccess"})
public class ParametrosPeticion {
    private Method     method;
    private String     url;
    private JSONObject JSONObject;
    private JSONArray  JSONArray;
    private JsonTypes  jsonType;
    private Class      clase;

    public enum Method {
        POST,
        GET
    }

    public enum JsonTypes {
        SIMPLE,
        ARRAY,
        ERROR
    }

    /**
     * Constructor ParametrosPeticion sin parámetros.
     * Por defecto, el tipo de método HTTP es 'GET'.
     *
     * @author Ventura de Lucas
     */
    public ParametrosPeticion() {
        method = Method.GET;
    }

    /**
     * Constructor ParametrosPeticion de 3 parámetros.
     *
     * @param method enum Method { POST, GET }. Método HTTP de la petición.
     * @param url    String. Dirección HTTP a la que se enviará la petición.
     * @param json   JSONObject. JSON que se enviará junto con la petición. En caso de no hacer falta, introducir un NULL.
     * @author Ventura de Lucas
     */
    public ParametrosPeticion(Method method, String url, JSONObject json) {
        this(method, url, json, null);
    }

    /**
     * Constructor ParametrosPeticion de 3 parámetros.
     *
     * @param method enum Method { POST, GET }. Método HTTP de la petición.
     * @param url    String. Dirección HTTP a la que se enviará la petición.
     * @param json   Object. (Se parseará el objeto a JSONObject) JSON que se enviará junto con la petición. En caso de no hacer falta, introducir un NULL.
     * @author Ventura de Lucas
     */
    public ParametrosPeticion(Method method, String url, Object json) {
        this(method, url, json, null);
    }

    /**
     * Constructor ParametrosPeticion de 4 parámetros.
     *
     * @param method enum Method { POST, GET }. Método HTTP de la petición.
     * @param url    String. Dirección HTTP a la que se enviará la petición.
     * @param json   JSONObject. JSON que se enviará junto con la petición. En caso de no hacer falta, introducir un NULL.
     * @param clase  Class. Tipo de objeto al que se convertirá la respuesta recibida por el servidor.
     * @author Ventura de Lucas
     */
    public ParametrosPeticion(Method method, String url, JSONObject json, Class clase) {
        this.method = method;
        this.url    = url;
        this.clase  = clase;
        JSONObject  = json;
        jsonType    = JsonTypes.SIMPLE;
    }

    /**
     * Constructor ParametrosPeticion de 4 parámetros.
     *
     * @param method enum Method { POST, GET }. Método HTTP de la petición.
     * @param url    String. Dirección HTTP a la que se enviará la petición.
     * @param json   Object. (Se parseará el objeto a JSONObject) JSON que se enviará junto con la petición. En caso de no hacer falta, introducir un NULL.
     * @param clase  Class. Tipo de objeto al que se convertirá la respuesta recibida por el servidor.
     * @author Ventura de Lucas
     */
    public ParametrosPeticion(Method method, String url, Object json, Class clase) {
        this.method = method;
        this.url    = url;
        this.clase  = clase;
        try {
            jsonType   = JsonTypes.SIMPLE;
            JSONObject = new JSONObject(new Gson().toJson(json));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public int getMethod() {
        int m = 0;
        if (method == Method.POST) m = Request.Method.POST;
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
        JSONArray       = null;
        jsonType        = JsonTypes.SIMPLE;
    }

    public JSONArray getJSONArray() {
        return JSONArray;
    }

    public void setJSONArray(JSONArray JSONArray) {
        this.JSONArray = JSONArray;
        JSONObject     = null;
        jsonType       = JsonTypes.ARRAY;
    }

    public JsonTypes getJsonType() {
        return jsonType;
    }

    public Class getClase() {
        return clase;
    }

    public void setClase(Class clase) {
        this.clase = clase;
    }

    @NonNull
    @Override
    public String toString() {
        return "ParametrosPeticion{" +
                "method=" + method +
                ", url='" + url + '\'' +
                ", JSONObject=" + JSONObject +
                ", JSONArray=" + JSONArray +
                ", jsonType=" + jsonType +
                ", clase=" + clase +
                '}';
    }
}
