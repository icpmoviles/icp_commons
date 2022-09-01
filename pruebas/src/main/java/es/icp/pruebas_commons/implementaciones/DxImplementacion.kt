package es.icp.pruebas_commons.implementaciones

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
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
            .createDialog(fullScreen = true, animarAlEsconder = false, gravity = Gravity.CENTER)
            .setTitulo("Titulo ejemplo", context.getColor(R.color.darkRed))
            .setMensaje("Mensaje ejemplo")
            .setIcono(color = context.getColor(R.color.colorAccent))
            .noPermitirSalirSinBotones()
            .showAceptarButton(texto = "SI", context.getColor(R.color.btnOrange)) { onAccept.invoke() }
            .showCancelarButton { onCancel.invoke() }
            .showDialogReturnDxCustom()



    }

    fun mostrarDxNotification(context: Context, parentView: View){

        DxCustom(context).createNotification(parentView,
            "Me has pulsado 💖 💋",
            backgroundColor = context.getColor(R.color.lightGreen),
            strokeColor = context.getColor(R.color.red),
        )

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
            .createDialog(fullScreen = true, animarAlEsconder = false)
            .setTitulo("Titulo ejemplo con custom view", context.getColor(R.color.dxCustom), 19f)
            .setMensaje("Mensaje ejemplo con custom view", context.getColor(R.color.dxCustom),16f)
            .setIcono(color = context.getColor(R.color.dxCustom))
            .permitirSalirSinBotones()
            .showAceptarButton("SI", context.getColor(R.color.red)) { onAccept.invoke(binding) }
            .showCancelarButton(
                strokecolor = R.color.colorAccent,
                textColor = context.getColor(R.color.darkRed)
            ) { onCancel.invoke() }
            .showDialogReturnDxCustom()
            .addCustomView(binding.root)
            .showDialogReturnDxCustom()
    }


}