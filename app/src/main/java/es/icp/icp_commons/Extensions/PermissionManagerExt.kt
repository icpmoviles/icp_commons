package es.icp.icp_commons.Extensions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun checkAppPermissions(context: Context, activityResultLauncher: ActivityResultLauncher<Array<String>>, permisos: Array<String> ){
    permisos.forEach { permission ->
        if (ContextCompat.checkSelfPermission(context, permission) ==
            PackageManager.PERMISSION_DENIED) {
            activityResultLauncher.launch(permisos)
            return
        }
    }
}
/**
 * En fragment instanciar y comprobar permisos dentro de override onViewStateRestored()
 *
 * var requestMultiplePermissions: ActivityResultLauncher<Array<String>> =
 *         getResultLauncher(
 *              activity,
 *              onDenied = { Aviso de que debe aceptar los permisos},
 *              onNeverAskAgain = {Avisar de que debe acpetar manualmente los permisos}
 *         )
 *
 *  DESPUES LLAMAMOS A ESTE METEDO PASANDO EL RESULT LAUNCHER
 * checkAppPermissions(CONTEXTO, ACTIVITYRESULTLAUNCHER, ARRAY DE PERMISOS)
 *
 */
fun getResultLauncher (
    activity: ComponentActivity,
    onDenied: () -> Unit = {},
    onNeverAskAgain: () -> Unit = {}
):  ActivityResultLauncher<Array<String>> {

    return activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        permissions.entries.forEach {
            val granted = it.value
            val permission = it.key
            if (!granted) {

                val neverAskAgain = !ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    permission
                )
                if (neverAskAgain) {
                    //user click "never ask again"
                    onNeverAskAgain()
                } else {
                    //show explain dialog
                    onDenied()
                }
                return@registerForActivityResult
            }
        }
    }
}

/**
 * ABRE LOS AJUSTES DE LA APP
 */
fun Context.openAppSettings() {
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        addCategory(Intent.CATEGORY_DEFAULT)
        data = Uri.parse("package:$packageName")
    }.let(::startActivity)
}

fun View.snackBarAjustes (message: String) =
    Snackbar.make(context, this, message, Snackbar.LENGTH_INDEFINITE ).setAction("Activar", View.OnClickListener {
        context.openAppSettings()
    }).setActionTextColor(Color.MAGENTA).show()
