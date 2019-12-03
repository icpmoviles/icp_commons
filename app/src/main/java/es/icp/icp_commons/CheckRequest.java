package es.icp.icp_commons;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

import es.icp.icp_commons.Helpers.GlobalVariables;
import es.icp.icp_commons.Helpers.Helper;
import es.icp.icp_commons.Interfaces.EnvioAccionesCallback;
import es.icp.icp_commons.Interfaces.VolleyCallBack;
import es.icp.icp_commons.Objects.ParametrosPeticion;
import es.icp.icp_commons.Services.WebService;

import static es.icp.icp_commons.Services.WebService.EnviarAcciones;
import static es.icp.icp_commons.Services.WebService.ShowLoading;

public class CheckRequest {
    private static Context context;
    private static VolleyCallBack callBack;

    public static void Check(Context context, EnvioAccionesCallback envioAccionesCallback) {
        Check(context, envioAccionesCallback, true);
    }

    public static void Check(Context context, EnvioAccionesCallback envioAccionesCallback, boolean loader) {
        CheckRequest.context = context;
        GlobalVariables.loader = loader;
        try{
            if (Helper.CheckConnection(context))
            {
                EnviarAcciones(context, envioAccionesCallback);

            }else{
                envioAccionesCallback.onOffline();
            }
        }catch (Exception ex)
        {
            envioAccionesCallback.onOffline();
        }
    }

    public static boolean CheckConnection(Context context) {
        return Helper.CheckConnection(context);
    }

    public static void Send(final Context context, final ParametrosPeticion parametros, final VolleyCallBack callBack) {
        Send(context, parametros, callBack, true);
    }

    public static void Send(final Context context, final ParametrosPeticion parametros, final VolleyCallBack callBack, final boolean loader) {
        CheckRequest.callBack = callBack;
        CheckRequest.context = context;
        GlobalVariables.loader = loader;
        try {

            if (GlobalVariables.loader) ShowLoading(context);

            if (parametros.getJsonType() == ParametrosPeticion.JsonTypes.SIMPLE) {
                JsonObjectRequest request = new JsonObjectRequest(parametros.getMethod(), parametros.getUrl(), parametros.getJSONObject(),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if (GlobalVariables.loader) WebService.HideLoading();
                                Object responseObject = null;
                                try {
                                    Class clase = parametros.getClase();
                                    if (clase != null) {
                                        if (clase.isInstance(new Array[]{})) {
                                            responseObject = new Gson().fromJson(response.getJSONArray("data").toString(), clase);
                                        } else {
                                            responseObject = new Gson().fromJson(response.getJSONObject("data").toString(), clase);
                                        }
                                    } else {
                                        responseObject = response;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    responseObject = response;
                                }
                                callBack.onSuccess(responseObject);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (GlobalVariables.loader) WebService.HideLoading();
                        callBack.onError((es.icp.icp_commons.Objects.VolleyError) error);
                    }
                });
                WebService.AddRequest(request, context);
            } // else if (parametros.getJsonType() == ParametrosPeticion.JsonTypes.ARRAY) {
//                JsonArrayRequest request = new JsonArrayRequest(parametros.getMethod(), parametros.getUrl(), parametros.getJSONArray(),
//                        new Response.Listener<JSONArray>() {
//                            @Override
//                            public void onResponse(JSONArray response) {
//                                if (GlobalVariables.loader) WebService.HideLoading();
//                                callBack.onSuccess(response);
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        if (GlobalVariables.loader) WebService.HideLoading();
//                        callBack.onError(error);
//                    }
//                });
//            }

        } catch (Exception ex) {
            if (GlobalVariables.loader) WebService.HideLoading();
            ex.printStackTrace();
            callBack.onOffline();
        }
    }

    public static void CheckAndSend(final Context context, final ParametrosPeticion parametros, final VolleyCallBack callBack) {
        CheckAndSend(context, parametros, callBack, true);
    }

    public static void CheckAndSend(final Context context, final ParametrosPeticion parametros, final VolleyCallBack callBack, final boolean loader) {
        CheckRequest.callBack = callBack;
        CheckRequest.context = context;
        GlobalVariables.loader = loader;

        Check(context, new EnvioAccionesCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFinish() {
                Send(context, parametros, callBack, loader);
            }

            @Override
            public void onOffline() {
                callBack.onOffline();
            }
        }, loader);
    }

    public static void CheckAndSendWithoutActions(final Context context, final ParametrosPeticion parametros, final VolleyCallBack callBack) {
        CheckAndSendWithoutActions(context, parametros, callBack, true);
    }

    public static void CheckAndSendWithoutActions(final Context context, final ParametrosPeticion parametros, final VolleyCallBack callBack, final boolean loader) {
        CheckRequest.callBack = callBack;
        CheckRequest.context = context;
        GlobalVariables.loader = loader;

        if (Helper.CheckConnection(context)) {
            Send(context, parametros, callBack, loader);
        } else {
            callBack.onOffline();
        }
    }
}
