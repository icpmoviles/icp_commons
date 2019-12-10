package es.icp.icp_commons;

import android.app.Activity;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import es.icp.icp_commons.Helpers.GlobalVariables;
import es.icp.icp_commons.Helpers.Helper;
import es.icp.icp_commons.Interfaces.EnvioAccionesCallback;
import es.icp.icp_commons.Interfaces.VolleyCallBack;
import es.icp.icp_commons.Objects.CheckRequestException;
import es.icp.icp_commons.Objects.ParametrosPeticion;
import es.icp.icp_commons.Services.WSHelper;
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
     * @param idUsuario int. ID del usuario que está llamando al servicio.
     * @param  urlError String. URL a la cual se mandará un log con errores (en caso de que ocurran). Introducir "" en caso de no querer enviar el log al servicio.
     */
    public static void Check(Context context, EnvioAccionesCallback envioAccionesCallback, int idUsuario, String urlError) throws CheckRequestException {
        Check(context, envioAccionesCallback, true, idUsuario, urlError);
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
     * @param idUsuario int. ID del usuario que está llamando al servicio.
     * @param  urlError String. URL a la cual se mandará un log con errores (en caso de que ocurran). Introducir "" en caso de no querer enviar el log al servicio.
     */
    public static void Check(Context context, final EnvioAccionesCallback envioAccionesCallback, boolean loader, int idUsuario, String urlError) throws CheckRequestException {
        GlobalVariables.loader = loader;
        try{
            if (Helper.CheckConnection(context))
            {
                EnviarAcciones(context, envioAccionesCallback);

            }else{
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        envioAccionesCallback.onOffline();
                    }
                });
            }
        }catch (Exception e)
        {
            WebService.TratarExcepcion(context, e.getMessage(), idUsuario, "Before Sending Request - Check", e, "", urlError);
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
     * @param idUsuario int. ID del usuario que está llamando al servicio.
     * @param  urlError String. URL a la cual se mandará un log con errores (en caso de que ocurran). Introducir "" en caso de no querer enviar el log al servicio.
     */
    public static void Send(final Context context, final ParametrosPeticion parametros, final VolleyCallBack callBack, int idUsuario, String urlError) throws CheckRequestException {
        Send(context, parametros, callBack, true, idUsuario, urlError);
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
     * @param idUsuario int. ID del usuario que está llamando al servicio.
     * @param  urlError String. URL a la cual se mandará un log con errores (en caso de que ocurran). Introducir "" en caso de no querer enviar el log al servicio.
     */
    public static void Send(final Context context, final ParametrosPeticion parametros, final VolleyCallBack callBack,
                            final boolean loader, int idUsuario, String urlError) throws CheckRequestException {
        GlobalVariables.loader = loader;
        WSHelper.logWS(parametros.getUrl(), parametros.getJSONObject());
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
                                        Object objetoAux;
                                        if (clase != null) {
                                            String data;
                                            try {
                                                data = response.getJSONObject("data").toString();
                                            } catch (JSONException e) {
                                                data = response.getJSONArray("data").toString();
                                            }
                                            if (data.startsWith("[")) {
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
                            callBack.onError((error.getMessage() == null) ? "Error " + error.networkResponse.statusCode + context.getString(R.string.contacte_administrador_error) : error.getMessage());
                        }
                    });
                    WebService.AddRequest(request, context);
                } catch (Exception e) {
                    if (GlobalVariables.loader) Loading.HideLoading();
                    WebService.TratarExcepcion(context, e.getMessage(), idUsuario, "Request error - Send", e, "", urlError);
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
            WebService.TratarExcepcion(context, e.getMessage(), idUsuario, "Request error - Send", e, "", urlError);
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
     * En caso de no introducir el parámetro 'guardarAccion' no se almacenará la petición en caso 'offline'
     *
     * @author Ventura de Lucas
     * @param context Context. Contexto de la aplicación.
     * @param parametros ParametrosPeticion. Objeto con los distintos parámetros de la petición y clase de respuesta a recibir.
     * @param callBack VolleyCallBack. Listener con el resultado del envío.
     * @param idUsuario int. ID del usuario que está llamando al servicio.
     * @param urlError String. URL a la cual se mandará un log con errores (en caso de que ocurran). Introducir "" en caso de no querer enviar el log al servicio.
     * @throws CheckRequestException Hereda de Exception. Nos proporciona la propiedad function (String).
     */
    public static void CheckAndSend(final Context context, final ParametrosPeticion parametros, final VolleyCallBack callBack, int idUsuario, String urlError) throws CheckRequestException {
        CheckAndSend(context, parametros, callBack, true, idUsuario, urlError, false);
    }

    /**
     * Realiza las comprobaciones anteriores al envío de la petición al servidor.
     * Comprueba la conexión a Internet.
     * Comprueba si existen acciones esperando a ser enviadas. En caso afirmativo, las envía.
     * Porteriormente, envía la petición al servidor.
     * Método asíncrono. Listener de tipo VolleyCallBack.
     * En caso de no introducir el parámetro 'guardarAccion' no se almacenará la petición en caso 'offline'
     *
     * @author Ventura de Lucas
     * @param context Context. Contexto de la aplicación.
     * @param parametros ParametrosPeticion. Objeto con los distintos parámetros de la petición y clase de respuesta a recibir.
     * @param callBack VolleyCallBack. Listener con el resultado del envío.
     * @param idUsuario int. ID del usuario que está llamando al servicio.
     * @param urlError String. URL a la cual se mandará un log con errores (en caso de que ocurran). Introducir "" en caso de no querer enviar el log al servicio.
     * @param guardarAccion boolean. Indica si se quiere almacenar la petición en caso 'offline'.
     * @throws CheckRequestException Hereda de Exception. Nos proporciona la propiedad function (String).
     */
    public static void CheckAndSend(final Context context, final ParametrosPeticion parametros, final VolleyCallBack callBack, int idUsuario, String urlError, boolean guardarAccion) throws CheckRequestException {
        CheckAndSend(context, parametros, callBack, true, idUsuario, urlError, guardarAccion);
    }

    /**
     * Realiza las comprobaciones anteriores al envío de la petición al servidor.
     * Comprueba la conexión a Internet.
     * Comprueba si existen acciones esperando a ser enviadas. En caso afirmativo, las envía.
     * Porteriormente, envía la petición al servidor.
     * Método asíncrono. Listener de tipo VolleyCallBack.
     * En caso de no introducir el parámetro 'guardarAccion' no se almacenará la petición en caso 'offline'
     *
     * @author Ventura de Lucas
     * @param context Context. Contexto de la aplicación.
     * @param parametros ParametrosPeticion. Objeto con los distintos parámetros de la petición y clase de respuesta a recibir.
     * @param callBack VolleyCallBack. Listener con el resultado del envío.
     * @param idUsuario int. ID del usuario que está llamando al servicio.
     * @param urlError String. URL a la cual se mandará un log con errores (en caso de que ocurran). Introducir "" en caso de no querer enviar el log al servicio.
     * @throws CheckRequestException Hereda de Exception. Nos proporciona la propiedad function (String).
     */
    public static void CheckAndSend(final Context context, final ParametrosPeticion parametros, final VolleyCallBack callBack, boolean loader, int idUsuario, String urlError) throws CheckRequestException {
        CheckAndSend(context, parametros, callBack, loader, idUsuario, urlError, false);
    }

    /**
     * Realiza las comprobaciones anteriores al envío de la petición al servidor.
     * Comprueba la conexión a Internet.
     * Comprueba si existen acciones esperando a ser enviadas. En caso afirmativo, las envía.
     * Porteriormente, envía la petición al servidor.
     * Método asíncrono. Listener de tipo VolleyCallBack.
     * En caso de no introducir el parámetro 'guardarAccion' no se almacenará la petición en caso 'offline'
     *
     * @author Ventura de Lucas
     * @param context Context. Contexto de la aplicación.
     * @param parametros ParametrosPeticion. Objeto con los distintos parámetros de la petición y clase de respuesta a recibir.
     * @param callBack VolleyCallBack. Listener con el resultado del envío.
     * @param loader boolean. Indica si se quiere mostrar un loader hasta recibir respuesta del listener. Por defecto, se encuentra a 'true'.
     * @param idUsuario int. ID del usuario que está llamando al servicio.
     * @param urlError String. URL a la cual se mandará un log con errores (en caso de que ocurran). Introducir "" en caso de no querer enviar el log al servicio.
     * @param guardarAccion boolean. Indica si se quiere almacenar la petición en caso 'offline'.
     * @throws CheckRequestException Hereda de Exception. Nos proporciona la propiedad function (String).
     */
    public static void CheckAndSend(final Context context, final ParametrosPeticion parametros, final VolleyCallBack callBack,
                                    final boolean loader, final int idUsuario, final String urlError, final boolean guardarAccion) throws CheckRequestException {
        GlobalVariables.loader = loader;

        try {
            Check(context, new EnvioAccionesCallback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFinish() throws CheckRequestException {
                    Send(context, parametros, callBack, loader, idUsuario, urlError);
                }

                @Override
                public void onOffline() {
                    if (guardarAccion) {
                        AddAction.AddActionDatabase(parametros.getJSONObject().toString(), context, parametros.getUrl(), (parametros.getMethod() == Request.Method.POST) ? "POST" : "GET", "");
                    }
                    callBack.onOffline();
                }
            }, loader, idUsuario, urlError);
        } catch (CheckRequestException e) {
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
     * @param idUsuario int. ID del usuario que está llamando al servicio.
     * @param  urlError String. URL a la cual se mandará un log con errores (en caso de que ocurran). Introducir "" en caso de no querer enviar el log al servicio.
     */
    public static void CheckAndSendWithoutActions(final Context context, final ParametrosPeticion parametros,
                                                  final VolleyCallBack callBack, int idUsuario, String urlError) throws CheckRequestException {
        CheckAndSendWithoutActions(context, parametros, callBack, true, idUsuario, urlError);
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
     * @param idUsuario int. ID del usuario que está llamando al servicio.
     * @param  urlError String. URL a la cual se mandará un log con errores (en caso de que ocurran). Introducir "" en caso de no querer enviar el log al servicio.
     */
    public static void CheckAndSendWithoutActions(final Context context, final ParametrosPeticion parametros,
                                                  final VolleyCallBack callBack, final boolean loader, int idUsuario, String urlError) throws CheckRequestException {
        GlobalVariables.loader = loader;

        if (Helper.CheckConnection(context)) {
            Send(context, parametros, callBack, loader, idUsuario, urlError);
        } else {
            callBack.onOffline();
        }
    }
}
