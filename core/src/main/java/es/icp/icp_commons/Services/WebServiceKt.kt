package es.icp.icp_commons.Services

import android.content.Context
import android.util.Log
import com.android.volley.Request.Method.POST
import com.android.volley.VolleyError
import es.icp.icp_commons.CheckRequest
import es.icp.icp_commons.Helpers.Constantes
import es.icp.icp_commons.Interfaces.NewVolleyCallBack
import es.icp.icp_commons.Objects.ParametrosPeticion
import es.icp.icp_commons.R
import es.icp.logs.core.MyLog
import org.json.JSONException
import org.json.JSONObject

object WebServiceKt {
    fun procesarRequest(
        context: Context,
        request : JSONObject,
        method: Int,
        callback : Ws_Callback,
        url : String
    ) {
        val objectResponse = DefaultResult()
        val parametros = ParametrosPeticion()
        parametros.clase = objectResponse.javaClass
        parametros.url = url
        if (method == POST) {
            parametros.setMethod(ParametrosPeticion.Method.POST)
        } else {
            parametros.setMethod(ParametrosPeticion.Method.GET)
        }
        parametros.jsonObject = request

        try {
            CheckRequest.CheckAndSend(context, parametros, object : NewVolleyCallBack {
                override fun onSuccess(result: Any?) {
                    MyLog.i("JSON: ${result.toString()}")
                    if (result is DefaultResult) {
                        callback.online(result)
                    } else {
                        Log.e("ERROR","ERROR" + "{$result}")
                    }
                }

                override fun onError(error: VolleyError?) {
                    val a = error?.let {
                        error.message?.let { it1 -> Log.e("ERROR", it1) }
                    } ?: run {
                        Log.e("ERROR", "Se ha producido un error")
                    }
                }

                override fun onOffline() {
                    callback.offline()
                }

            }, false)
        } catch (ex : Exception) {
            ex.printStackTrace()
        }



    }


}