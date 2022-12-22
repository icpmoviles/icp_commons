package es.icp.icp_commons.camara.helpers

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

object Utils {

    fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    fun comprobarPermisos(context: Context?, permisos: Array<String>): Boolean {
        return if (hasPermissions(context, *permisos)) {
            ActivityCompat.requestPermissions(
                (context as Activity?)!!,
                permisos,
                Constantes.CODE_PERMISSIONS
            )
            false
        } else true
    }

}