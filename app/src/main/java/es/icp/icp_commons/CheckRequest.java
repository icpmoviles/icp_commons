package es.icp.icp_commons;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import es.icp.icp_commons.Helpers.Helper;
import es.icp.icp_commons.Interfaces.EnvioAccionesCallback;
import es.icp.icp_commons.Interfaces.VolleyCallBack;
import es.icp.icp_commons.Services.WebService;

import static es.icp.icp_commons.Services.WebService.EnviarAcciones;
import static es.icp.icp_commons.Services.WebService.ShowLoading;

public class CheckRequest {
    private static Context context;
    private static VolleyCallBack callBack;

    public static void Check(final Context context, final JsonObjectRequest jsonObjectRequest, final VolleyCallBack callback) {
        CheckRequest.callBack = callback;
        CheckRequest.context = context;
        try{
            if (Helper.CheckConnection(context))
            {
                EnviarAcciones(context, new EnvioAccionesCallback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFinish() {
                        try {

                            ShowLoading(context);

                            JsonObjectRequest request = new JsonObjectRequest(jsonObjectRequest.getMethod(), jsonObjectRequest.getUrl(), new JSONObject(jsonObjectRequest.getBody().toString()),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    WebService.HideLoading();
                                    callBack.onSuccess(response);
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    WebService.HideLoading();
                                    callBack.onError(error);
                                    jsonObjectRequest.getErrorListener().onErrorResponse(error);
                                }
                            });

                            WebService.AddRequest(request, context);
                        } catch (Exception ex) {
                            WebService.HideLoading();
                            ex.printStackTrace();
                            callBack.onOffline();
                        }
                    }
                });

            }else{
                callBack.onOffline();
            }
        }catch (Exception ex)
        {

        }
    }

}
