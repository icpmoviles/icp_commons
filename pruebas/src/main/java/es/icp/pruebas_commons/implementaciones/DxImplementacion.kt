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
            .createDialog(fullScreen = true)
            .setTitulo("Titulo ejemplo")
            .setMensaje("Mensaje ejemplo")
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
            .setTitulo("Titulo ejemplo con custom view")
            .setMensaje("Mensaje ejemplo con custom view")
            .permitirSalirSinBotones()
            .showAceptarButton(texto = "SI") { onAccept.invoke(binding) }
            .showCancelarButton { onCancel.invoke() }
            .showDialogReturnDxCustom()
            .addCustomView(binding.root)
            .showDialogReturnDxCustom()
    }


}