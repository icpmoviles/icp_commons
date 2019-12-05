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
import es.icp.icp_commons.Objects.CheckRequestException;
import es.icp.icp_commons.Objects.ParametrosPeticion;
import es.icp.icp_commons.Services.WebService;

import static es.icp.icp_commons.Services.WebService.EnviarAcciones;


@SuppressWarnings({"unused", "WeakerAccess"})
public class CheckRequest {

    /**
     * Realiza las comprobaciones anteriores al envío de una petición al servidor.
     * Comprueba la conexión a Internet.
     * Comprueba si existen acciones esperando a ser enviadas. En caso afirmativo, las envía.
     * Método asíncrono. Listener de tipo EnvioAccionesCallback.
     *
     * @author Ventura de Lucas
     * @param context Context. Contexto de la aplicación.
     * @param envioAccionesCallback EnvioAccionesCallback. Listener con el resultado de las comprobaciones.
     */
    public static void Check(Context context, EnvioAccionesCallback envioAccionesCallback) throws CheckRequestException {
        Check(context, envioAccionesCallback, true);
    }

    /**
     * Realiza las comprobaciones anteriores al envío de una petición al servidor.
     * Comprueba la conexión a Internet.
     * Comprueba si existen acciones esperando a ser enviadas. En caso afirmativo, las envía.
     * Método asíncrono. Listener de tipo EnvioAccionesCallback.
     *
     * @author Ventura de Lucas
     * @param context Context. Contexto de la aplicación.
     * @param envioAccionesCallback EnvioAccionesCallback. Listener con el resultado de las comprobaciones.
     * @param loader boolean. Indica si se quiere mostrar un loader hasta recibir respuesta del listener. Por defecto, se encuentra a 'true'.
     */
    public static void Check(Context context, EnvioAccionesCallback envioAccionesCallback, boolean loader) throws CheckRequestException {
        GlobalVariables.loader = loader;
        try{
            if (Helper.CheckConnection(context))
            {
                EnviarAcciones(context, envioAccionesCallback);

            }else{
                envioAccionesCallback.onOffline();
            }
        }catch (Exception e)
        {
//            envioAccionesCallback.onOffline();
            throw createException(e, "Before Sending Request", "Check");
        }
    }

    /**
     * Comprueba la conexión a Internet.
     *
     * @param context Context. Contexto de la aplicación.
     * @return Devuelve un 'boolean' indicando si hay o no conexión.
     */
    public static boolean CheckConnection(Context context) {
        return Helper.CheckConnection(context);
    }

    /**
     * Envía una petición al servidor sin realizar ninguna comprobación antes del envío.
     * Método asíncrono. Listener de tipo VolleyCallBack.
     *
     * @author Ventura de Lucas
     * @param context Context. Contexto de la aplicación.
     * @param parametros ParametrosPeticion. Objeto con los distintos parámetros de la petición y clase de respuesta a recibir.
     * @param callBack VolleyCallBack. Listener con el resultado del envío.
     */
    public static void Send(final Context context, final ParametrosPeticion parametros, final VolleyCallBack callBack) throws CheckRequestException {
        Send(context, parametros, callBack, true);
    }

    /**
     * Envía una petición al servidor sin realizar ninguna comprobación antes del envío.
     * Método asíncrono. Listener de tipo VolleyCallBack.
     *
     * @author Ventura de Lucas
     * @param context Context. Contexto de la aplicación.
     * @param parametros ParametrosPeticion. Objeto con los distintos parámetros de la petición y clase de respuesta a recibir.
     * @param callBack VolleyCallBack. Listener con el resultado del envío.
     * @param loader boolean. Indica si se quiere mostrar un loader hasta recibir respuesta del listener. Por defecto, se encuentra a 'true'.
     */
    public static void Send(final Context context, final ParametrosPeticion parametros, final VolleyCallBack callBack,
                            final boolean loader) throws CheckRequestException {
        GlobalVariables.loader = loader;
        try {

            if (GlobalVariables.loader) Loading.ShowLoading(context);

            if (parametros.getJsonType() == ParametrosPeticion.JsonTypes.SIMPLE) {
                try {
                    JsonObjectRequest request = new JsonObjectRequest(parametros.getMethod(), parametros.getUrl(), parametros.getJSONObject(),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    if (GlobalVariables.loader) Loading.HideLoading();
                                    Object responseObject;
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
                            if (GlobalVariables.loader) Loading.HideLoading();
                            callBack.onError((error.getMessage() == null) ? String.valueOf(error.networkResponse.statusCode) : error.getMessage());
                        }
                    });
                    WebService.AddRequest(request, context);
                } catch (Exception e) {
                    if (GlobalVariables.loader) Loading.HideLoading();
                    throw createException(e, parametros.getUrl(), "Send");
                }

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

        } catch (Exception e) {
            if (GlobalVariables.loader) Loading.HideLoading();
//            e.printStackTrace();
//            callBack.onOffline();
            throw createException(e, parametros.getUrl(), "Send");
        }
    }

    private static CheckRequestException createException(Exception e, String url, String libMethod) {
        String function = "WebService Exception (problem in" + libMethod + " method) - " + url;
        CheckRequestException checkRequestException = ((CheckRequestException) e);
        checkRequestException.setFunction(function);
        return checkRequestException;
    }

    /**
     * Realiza las comprobaciones anteriores al envío de la petición al servidor.
     * Comprueba la conexión a Internet.
     * Comprueba si existen acciones esperando a ser enviadas. En caso afirmativo, las envía.
     * Porteriormente, envía la petición al servidor.
     * Método asíncrono. Listener de tipo VolleyCallBack.
     *
     * @author Ventura de Lucas
     * @param context Context. Contexto de la aplicación.
     * @param parametros ParametrosPeticion. Objeto con los distintos parámetros de la petición y clase de respuesta a recibir.
     * @param callBack VolleyCallBack. Listener con el resultado del envío.
     * @throws CheckRequestException Hereda de Exception. Nos proporciona la propiedad function (String).
     */
    public static void CheckAndSend(final Context context, final ParametrosPeticion parametros, final VolleyCallBack callBack) throws CheckRequestException {
        CheckAndSend(context, parametros, callBack, true);
    }

    /**
     * Realiza las comprobaciones anteriores al envío de la petición al servidor.
     * Comprueba la conexión a Internet.
     * Comprueba si existen acciones esperando a ser enviadas. En caso afirmativo, las envía.
     * Porteriormente, envía la petición al servidor.
     * Método asíncrono. Listener de tipo VolleyCallBack.
     *
     * @author Ventura de Lucas
     * @param context Context. Contexto de la aplicación.
     * @param parametros ParametrosPeticion. Objeto con los distintos parámetros de la petición y clase de respuesta a recibir.
     * @param callBack VolleyCallBack. Listener con el resultado del envío.
     * @param loader boolean. Indica si se quiere mostrar un loader hasta recibir respuesta del listener. Por defecto, se encuentra a 'true'.
     */
    public static void CheckAndSend(final Context context, final ParametrosPeticion parametros, final VolleyCallBack callBack,
                                    final boolean loader) throws CheckRequestException {
        GlobalVariables.loader = loader;

        try {
            Check(context, new EnvioAccionesCallback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFinish() throws CheckRequestException {
                    Send(context, parametros, callBack, loader);
                }

                @Override
                public void onOffline() {
                    callBack.onOffline();
                }
            }, loader);
        } catch (ClassCastException e) {
            throw e;
        }

    }

    /**
     * Solamente se comprueba la conexión a Internet. No se envían acciones en espera.
     * Porteriormente, envía la petición al servidor.
     * Método asíncrono. Listener de tipo VolleyCallBack.
     *
     * @author Ventura de Lucas
     * @param context Context. Contexto de la aplicación.
     * @param parametros ParametrosPeticion. Objeto con los distintos parámetros de la petición y clase de respuesta a recibir.
     * @param callBack VolleyCallBack. Listener con el resultado del envío.
     */
    public static void CheckAndSendWithoutActions(final Context context, final ParametrosPeticion parametros,
                                                  final VolleyCallBack callBack) throws CheckRequestException {
        CheckAndSendWithoutActions(context, parametros, callBack, true);
    }

    /**
     * Solamente se comprueba la conexión a Internet. No se envían acciones en espera.
     * Porteriormente, envía la petición al servidor.
     * Método asíncrono. Listener de tipo VolleyCallBack.
     *
     * @author Ventura de Lucas
     * @param context Context. Contexto de la aplicación.
     * @param parametros ParametrosPeticion. Objeto con los distintos parámetros de la petición y clase de respuesta a recibir.
     * @param callBack VolleyCallBack. Listener con el resultado del envío.
     * @param loader boolean. Indica si se quiere mostrar un loader hasta recibir respuesta del listener. Por defecto, se encuentra a 'true'.
     */
    public static void CheckAndSendWithoutActions(final Context context, final ParametrosPeticion parametros,
                                                  final VolleyCallBack callBack, final boolean loader) throws CheckRequestException {
        GlobalVariables.loader = loader;

        if (Helper.CheckConnection(context)) {
            Send(context, parametros, callBack, loader);
        } else {
            callBack.onOffline();
        }
    }
}
