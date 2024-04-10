package es.icp.icp_commons.Utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import es.icp.icp_commons.R

object UtilsKt {
    fun isMyServiceRunning(serviceClass: Class<*>, mActivity: Activity): Boolean {
        val manager: ActivityManager =
            mActivity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                Log.i("Service status", "Running")
                return true
            }
        }
        Log.i("Service status", "Not running")
        return false
    }

    fun setUpDialog(description: String, context: Context, onAceptar: () -> Unit) {

        // Padding del ul para separar de los bordes la lista
        // Margin del li para separar los elementos de la lista entre sí
        val htmlString = """
        <style>
            ul {
                padding: 0 25px;
            }

            li {
                margin-bottom: 10px;
            }
        </style>
            $description
    """.trimIndent()

        // Creamos el dialog
        val dialog = MaterialAlertDialogBuilder(context)
            .setCancelable(false)
            .create()

        // Creamos la vista del dialog y asignamos a una variable el WebView
        val dialogView =
            LayoutInflater.from(context).inflate(R.layout.update_dialog_layout, null)
        val webViewMessage = dialogView.findViewById<WebView>(R.id.webViewMessage)

        webViewMessage.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                // Mostramos el dialog después de que la página se ha cargado completamente
                dialog.show()
            }
        }

        // Cargamos la vista HTML en el WebView
        webViewMessage.loadDataWithBaseURL(null, htmlString, "text/html", "UTF-8", null)

        // Asignamos la vista al dialog
        dialog.setView(dialogView)

        dialogView.findViewById<Button>(R.id.buttonAccept).setOnClickListener {
            onAceptar()
            dialog.dismiss()
        }
    }
}