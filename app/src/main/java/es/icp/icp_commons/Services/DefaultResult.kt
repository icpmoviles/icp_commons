package es.icp.icp_commons.Services

import com.google.gson.Gson

class DefaultResult  {
    var RETCODE: Int = 0
    var MENSAJE: String = ""
    var MENSAJE_APP: String = ""
    var MENSAJE_INTERNO: String = ""
    var JSON_OUT = ""

    init {

    }

    override fun toString(): String {
        return Gson().toJson(this).toString()
    }
}