package es.icp.icp_commons.imageGallery

import android.content.Context
import androidx.core.content.ContextCompat
import es.icp.icp_commons.DxCustom.DxCustom
import es.icp.icp_commons.R

object DxImageGalleryImplementation {

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