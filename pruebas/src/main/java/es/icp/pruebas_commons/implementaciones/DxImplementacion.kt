package es.icp.pruebas_commons.implementaciones

import android.content.Context
import android.view.LayoutInflater
import es.icp.icp_commons.DxCustom.DxCustom
import es.icp.pruebas_commons.R
import es.icp.pruebas_commons.databinding.DxCustomSolicitarNombreBinding

object DxImplementacion {

    fun mostrarDxEjemplo(
        context: Context,
        onAccept: () -> Unit,
        onCancel: () -> Unit
    ){

        DxCustom(context)
            .createDialog(fullScreen = true, verticalAnimation = false)
            .setTitulo("Titulo ejemplo", context.getColor(R.color.darkRed))
            .setMensaje("Mensaje ejemplo")
            .setIcono(color = context.getColor(R.color.colorAccent))
            .noPermitirSalirSinBotones()
            .showAceptarButton(texto = "SI") { onAccept.invoke() }
            .showCancelarButton { onCancel.invoke() }
            .showDialogReturnDxCustom()


    }

    fun mostrarDxEjemploConCustomView(
        context: Context,
        onAccept: (binding: DxCustomSolicitarNombreBinding) -> Unit,
        onCancel: () -> Unit
    ){

        val binding = DxCustomSolicitarNombreBinding.inflate(
            LayoutInflater.from(context), null, false
        )

        DxCustom(context)
            .createDialog(fullScreen = true)
            .setTitulo("Titulo ejemplo con custom view", context.getColor(R.color.dxCustom), 19f)
            .setMensaje("Mensaje ejemplo con custom view", context.getColor(R.color.dxCustom),16f)
            .setIcono(color = context.getColor(R.color.dxCustom))
            .permitirSalirSinBotones()
            .showAceptarButton(texto = "SI") { onAccept.invoke(binding) }
            .showCancelarButton { onCancel.invoke() }
            .showDialogReturnDxCustom()
            .addCustomView(binding.root)
            .showDialogReturnDxCustom()
    }


}