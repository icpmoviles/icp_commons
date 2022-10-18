package es.icp.pruebas_commons.implementaciones

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import es.icp.icp_commons.DxCustom.DxCustom
import es.icp.pruebas_commons.R
import es.icp.pruebas_commons.databinding.DxCustomSolicitarNombreBinding

object DxImplementacion {

    fun loaderConTextoYMensaje(context: Context): Dialog {
        return DxCustom(context).createLoading(lottieMarginTop = -50)
    }

    fun loaderSinTextoYMensaje(context: Context): Dialog {
        return DxCustom(context).createLoading(lottie = R.raw.loading_alt_anim, lottieMarginTop = -50, soloAnimacion = true)
    }

    fun mostrarDxEjemplo(
        context: Context,
        onAccept: () -> Unit,
        onCancel: () -> Unit
    ){
        val binding = DxCustomSolicitarNombreBinding.inflate(
            LayoutInflater.from(context), null, false
        )
        DxCustom(context)
            .createDialog(fullScreen = true, animarAlEsconder = false, gravity = Gravity.CENTER)
            .setTitulo("Titulo ejemplo", context.getColor(R.color.darkRed))
            .setMensaje(null)
            .setIcono(null)
            .noPermitirSalirSinBotones()
            .showAceptarButton(texto = "SI", context.getColor(R.color.btnOrange), textColor = context.getColor(R.color.red)) { onAccept.invoke() }
            .showCancelarButton { onCancel.invoke() }
            .addCustomView(binding.root)
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

        Log.d("DxCustom", context.getColor(R.color.darkRed).toString())

        DxCustom(context)
            .createDialog(fullScreen = true, animarAlEsconder = false)
            .setTitulo("Titulo ejemplo con custom view", context.getColor(R.color.dxCustom), 19f)
            .setMensaje("Mensaje ejemplo con custom view", context.getColor(R.color.dxCustom),16f)
//            .setIcono(color = context.getColor(R.color.dxCustom))
            .setIcono(AppCompatResources.getDrawable(context, R.drawable.ic_perseo_logo_casco), null)
            .permitirSalirSinBotones()
            .showAceptarButton("Si", context.getColor(R.color.red), textAllCaps = true) { onAccept.invoke(binding) }
            .showCancelarButton(
                strokecolor = context.getColor(R.color.darkRed),
                textColor = context.getColor(R.color.darkRed),
                textAllCaps = false
            ) { onCancel.invoke() }
            .showDialogReturnDxCustom()
            .addCustomView(binding.root)
            .showDialogReturnDxCustom()
    }


}