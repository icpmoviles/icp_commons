package es.icp.icp_commons.Services;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import es.icp.icp_commons.CheckRequest;
import es.icp.icp_commons.CustomDialog;
import es.icp.icp_commons.Database.DBHandler;
import es.icp.icp_commons.Helpers.Constantes;
import es.icp.icp_commons.Helpers.GlobalVariables;
import es.icp.icp_commons.Interfaces.EnvioAccionesCallback;
import es.icp.icp_commons.Interfaces.VolleyCallBack;
import es.icp.icp_commons.Loading;
import es.icp.icp_commons.Objects.Accion;
import es.icp.icp_commons.Objects.CheckRequestException;
import es.icp.icp_commons.Objects.ParametrosPeticion;
import es.icp.icp_commons.R;
import es.icp.logs.core.MyLog;

import static es.icp.icp_commons.Helpers.Constantes.DIALOG_NORMAL;

public class WebService {

    private static Loading.LoaderType loaderType = Loading.LoaderType.NORMAL_DIALOG;

    private static RequestQueue requestQueue;

    public static Loading.LoaderType getLoaderType() {
        return loaderType;
    }

    public static void setLoaderType(Loading.LoaderType loaderType) {
        WebService.loaderType = loaderType;
    }

    /**
     * Método para tratar excepciones. Requiere de conexión a Internet para funcionar. Envía un log con la excepción introducida.
     * En caso de no introducir url, no envía nada. (solo para funciones internas)
     *
     * @param mContext   Context. Contexto de la aplicación.
     * @param metodo     String. Mensaje de la excepción.
     * @param idUsuario  int. ID del usuario con el que se provocó la excepción.
     * @param mensaje    String. Mensaje informativo sobre el error.
     * @param exception  Exception. Excepción a enviar al servicio.
     * @param parametros todo: por el momento enviar vacío ("").
     */
    public static void TratarExcepcion(final Context mContext, final String metodo, final int idUsuario, final String mensaje, final Exception exception, String parametros) {
        TratarExcepcion(mContext, metodo, idUsuario, mensaje, exception, parametros, "");
    }

    /**
     * Método para tratar excepciones. Requiere de conexión a Internet para funcionar. Envía un log con la excepción introducida.
     * En caso de no introducir url, no envía nada. (solo para funciones internas)
     *
     * @param mContext   Context. Contexto de la aplicación.
     * @param metodo     String. Mensaje de la excepción.
     * @param idUsuario  int. ID del usuario con el que se provocó la excepción.
     * @param mensaje    String. Mensaje informativo sobre el error.
     * @param exception  Exception. Excepción a enviar al servicio.
     * @param parametros todo: por el momento enviar vacío ("").
     * @param url        String. URL a la cual se enviará el error.
     */
    public static void TratarExcepcion(final Context mContext, final String metodo, final int idUsuario, final String mensaje, final Exception exception, String parametros, String url) {
        if (!url.equals("")) {

            MyLog.e("Exception:" + exception.toString());
            Writer writer = new StringWriter();
            exception.printStackTrace(new PrintWriter(writer));

            try {
                final JSONObject json = new JSONObject();
                json.put("METODO", metodo);
                json.put("ENTORNO", "APP");
                json.put("USUARIO", String.valueOf(idUsuario));
                json.put("MENSAJE", mensaje);
                json.put("ERROR", exception.toString());
                json.put("VERSION", mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode);
                json.put("PARAMETROS", parametros);

                CheckRequest.CheckAndSend(mContext, new ParametrosPeticion(ParametrosPeticion.Method.POST, url, json), new VolleyCallBack() {
                    @Override
                    public void onSuccess(Object result) {

                    }

                    @Override
                    public void onError(String error) {
                        CustomDialog dialog = new CustomDialog(mContext, "Error: " + error, DIALOG_NORMAL);
                        dialog.Show();
                    }

                    @Override
                    public void onOffline() {

                    }
                }, false, idUsuario, "");
            } catch (JSONException | CheckRequestException | PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void EnviarAcciones(final Context mContext, final EnvioAccionesCallback callback) throws CheckRequestException {
        final DBHandler    dbHandler = new DBHandler(mContext);
        final List<Accion> acciones  = dbHandler.getAcciones();

        if (acciones.size() > 0) {
            ProgressDialog mProgres = null;
            try {
                switch (loaderType) {
                    case NORMAL_DIALOG:
                        mProgres = new ProgressDialog(mContext);
                        mProgres.setCancelable(false);
                        mProgres.setMessage(mContext.getString(R.string.subiendo_acciones_pendientes_offline));
                        if (GlobalVariables.loader) mProgres.show();
                        break;
                    case SMART_DIALOG:
                        Loading.ShowSmartLoading(mContext, mContext.getString(R.string.cargando), mContext.getString(R.string.subiendo_acciones_pendientes_offline), false);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            final ProgressDialog mProgress = mProgres;

            EnviarAccion(dbHandler, mContext, acciones, 0, acciones.size(), new EnvioAccionesCallback() {
                @Override
                public void onSuccess() {
                    Log.d("FALLO_LOADER", "onSuccess() - EnviarAccion()");
                }

                @Override
                public void onFinish() throws CheckRequestException {
                    closeLoadingDialog(mProgress);
                    callback.onFinish();

                }

                @Override
                public void onOffline() {
                    callback.onOffline();
                    closeLoadingDialog(mProgress);
                }
            });
        } else {
            callback.onFinish();
        }
    }

    private static void closeLoadingDialog(ProgressDialog mProgress) {
        try {
            switch (loaderType) {
                case NORMAL_DIALOG:
                    if (GlobalVariables.loader && mProgress != null) {
                        mProgress.hide();
                        mProgress.dismiss();
                    }
                    break;
                case SMART_DIALOG:
                    Loading.HideSmartLoading();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void EnviarAccion(final DBHandler dbHandler, final Context mContext, final List<Accion> acciones, final int contador, final int maximo, final EnvioAccionesCallback callback) throws CheckRequestException {
        try {
            if (contador < maximo) {
                //ENVIO LOS DATOS
                final Accion accion = acciones.get(contador);
                String       url    = accion.getURL();
                Log.d("DEBUG_ICP", "Se está mandando una acción desde el COMMONS!!!");
                WSHelper.logWS(url, accion.getJSON());
                if (accion.getMetodo().equals("POST")) {
                    try {
                        int method = accion.getMetodo().toUpperCase().equals("POST") ? Request.Method.POST : Request.Method.GET;

                        if (accion.getJSON().startsWith("[")) {
                            JSONArray json = new JSONArray(accion.getJSON());
                            JsonArrayRequest request = new JsonArrayRequest(method, url, json, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    dbHandler.removeAccion(accion.getID());
                                    try {
                                        EnviarAccion(dbHandler, mContext, acciones, contador + 1, maximo, null);
                                    } catch (CheckRequestException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                }
                            });
                            requestQueue.add(request);
                        } else {
                            JSONObject json = null;
                            if (!accion.getJSON().isEmpty()) json = new JSONObject(accion.getJSON());
                            JsonObjectRequest request = new JsonObjectRequest(method, url, json, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    dbHandler.removeAccion(accion.getID());
                                    try {
                                        EnviarAccion(dbHandler, mContext, acciones, contador + 1, maximo, callback);
                                    } catch (CheckRequestException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    error.printStackTrace();
                                    try {
                                        callback.onFinish();
                                    } catch (CheckRequestException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            AddRequest(request, mContext);
                        }
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                } else if (accion.getMetodo().equals("GET")) {
                    try {
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                dbHandler.removeAccion(accion.getID());
                                try {
                                    EnviarAccion(dbHandler, mContext, acciones, contador + 1, maximo, callback);
                                } catch (CheckRequestException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("ERRORACCION", "error en accion " + error.getMessage());
                                error.printStackTrace();
                                try {
                                    callback.onFinish();
                                } catch (CheckRequestException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        requestQueue.add(request);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                if (callback != null)
                    callback.onFinish();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            if (callback != null)
                callback.onFinish();
        }

    }

    public static void AddRequest(JsonObjectRequest request, Context mContext) {
        if (requestQueue == null) requestQueue = Volley.newRequestQueue(mContext);

        request.setRetryPolicy(new DefaultRetryPolicy(Constantes.TIMEOUT, Constantes.NUM_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

    public static void AddRequest(JsonArrayRequest request, Context mContext) {
        if (requestQueue == null) requestQueue = Volley.newRequestQueue(mContext);

        request.setRetryPolicy(new DefaultRetryPolicy(Constantes.TIMEOUT, Constantes.NUM_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }


}
