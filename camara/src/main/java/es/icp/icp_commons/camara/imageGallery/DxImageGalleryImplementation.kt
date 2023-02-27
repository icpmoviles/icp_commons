package es.icp.icp_commons.camara.imageGallery

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import es.icp.icp_commons.camara.DxCustom.DxCustom
import es.icp.icp_commons.camara.R

object DxImageGalleryImplementation {

    @RequiresApi(Build.VERSION_CODES.N)
    fun mostrarDxConfirmarBorrado(
        context: Context,
        numeroDeElementosABorrar: Int,
        onAccept: () -> Unit
    ) {
        val icono = ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_outline_24)
        icono?.setTint(context.getColor(R.color.red))

        DxCustom(context)
            .createDialog(true)
            .setTitulo("PRUEBA")
            .setMensaje("¿Está seguro de que desea eliminar $numeroDeElementosABorrar imágenes?")
            .setIcono(icono)
            .noPermitirSalirSinBotones()
            .showAceptarButton("Si") {
                onAccept.invoke()
            }
            .showCancelarButton("No") { }
            .showDialogReturnDialog()

    }

}