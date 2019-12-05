package es.icp.icp_commons.Services;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import es.icp.icp_commons.Database.DBHandler;
import es.icp.icp_commons.Helpers.Constantes;
import es.icp.icp_commons.Helpers.GlobalVariables;
import es.icp.icp_commons.Interfaces.EnvioAccionesCallback;
import es.icp.icp_commons.Objects.Accion;
import es.icp.icp_commons.Objects.CheckRequestException;
import es.icp.icp_commons.R;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class WebService {


    private static RequestQueue requestQueue;

    public static void EnviarAcciones(final Context mContext, final EnvioAccionesCallback callback) throws CheckRequestException {
        final DBHandler dbHandler = new DBHandler(mContext);
        final List<Accion> acciones = dbHandler.getAcciones();

        if (acciones.size() > 0)
        {
            final ProgressDialog mProgress = new ProgressDialog(mContext);
            mProgress.setCancelable(false);
            mProgress.setMessage(mContext.getString(R.string.subiendo_acciones_pendientes_offline));
            if (GlobalVariables.loader) mProgress.show();

            EnviarAccion(dbHandler, mContext, acciones, 0, acciones.size(), new EnvioAccionesCallback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onFinish() throws CheckRequestException {
                    if (GlobalVariables.loader && mProgress != null)
                    {
                        mProgress.hide();
                        mProgress.dismiss();
                    }
                    callback.onFinish();

                }

                @Override
                public void onOffline() {
                    callback.onOffline();
                }
            });
        }else{
            callback.onFinish();
        }
    }

    private static void EnviarAccion(final DBHandler dbHandler, final Context mContext, final List<Accion> acciones, final int contador, final int maximo, final EnvioAccionesCallback callback) throws CheckRequestException {
        try{
            if (contador < maximo)
            {
                //ENVIO LOS DATOS
                final Accion accion = acciones.get(contador);
                String url = accion.getURL();
                if (accion.getMetodo().equals("POST"))
                {
                    try
                    {

                        if (accion.getJSON().startsWith("["))
                        {
                            JSONArray json = new JSONArray(accion.getJSON());
                            JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, url, json, new Response.Listener<JSONArray>() {
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
                        }else
                        {
                            JSONObject json = new JSONObject(accion.getJSON());
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    dbHandler.removeAccion(accion.getID());
                                    try {
                                        EnviarAccion(dbHandler, mContext,acciones, contador + 1, maximo, callback);
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
                            AddRequest(request, mContext);
                        }
                    }catch (JSONException ex)
                    {
                        ex.printStackTrace();
                    }
                }else if (accion.getMetodo().equals("GET"))
                {
                    try
                    {
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                dbHandler.removeAccion(accion.getID());
                                try {
                                    EnviarAccion(dbHandler,mContext, acciones, contador + 1, maximo, callback);
                                } catch (CheckRequestException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("ERRORACCION", "error en accion " + error.getMessage());
                            }
                        });
                        requestQueue.add(request);
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }else{
                if (callback != null)
                    callback.onFinish();

            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
            if(callback != null)
                callback.onFinish();
        }

    }

    public static void AddRequest(JsonObjectRequest request, Context mContext){
        if (requestQueue == null) requestQueue = Volley.newRequestQueue(mContext);

        request.setRetryPolicy(new DefaultRetryPolicy(Constantes.TIMEOUT, Constantes.NUM_RETRY, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }


}
