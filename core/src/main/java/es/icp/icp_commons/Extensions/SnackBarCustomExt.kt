package es.icp.icp_commons.Extensions

import android.app.Activity
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import es.icp.icp_commons.ExplosionView.ExplosionAnimator
import es.icp.icp_commons.ExplosionView.ExplosionField
import es.icp.icp_commons.databinding.SnackbarCustomLayoutBinding

/**
 * @author Carlos del Campo Cebrian
 * @version 1.0
 *
 */


/**
 * Crea una snackbar personalizada
 * Params:
 *       mensaje -> Texto a mostrar
 *       colorBackGround -> Color de fondo, si es null se usa gris
 *       colorText -> color del texto, si se pasa null se usa white
 *       icono -> icono para mostrar en la snac
 *       gravity -> Indicar si queremos mostrar debajo, arriba o en el medio. Gravity.TOP | Gravity.BOTTOM | Gravity.CENTER
 *       conExplosion -> booleano para indicar si queremos animar con una explosion la salida del snack. True -> si / False -> no
 *       delayExplosion -> tiempo de retardo en la explosion 3000 milisegundo por defecto
 *
 */
fun View.snackPlosionBar (
    mensaje: String,
    colorBackGround: Int?,
    icono: Int?,
    colorText: Int?,
    gravity: Int,
    conExplosion: Boolean,
    delayExplosion: Long = 3000
)  {

    val explosionField = ExplosionField.attach2Window(this.context as Activity)

    val snackbar = Snackbar.make(this, "", Snackbar.LENGTH_LONG)
    snackbar.view.setBackgroundColor(Color.TRANSPARENT)

    val snackBarLayout = snackbar.view as Snackbar.SnackbarLayout
    snackBarLayout.removeAllViewsInLayout()
    snackBarLayout.setPadding(0,25,0,0)

    val params: FrameLayout.LayoutParams = snackBarLayout.layoutParams as FrameLayout.LayoutParams
    params.gravity = gravity

    when (gravity){
        Gravity.TOP -> {
            snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
            params.topMargin = 25
        }
        Gravity.CENTER -> snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        Gravity.BOTTOM -> snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
    }

    snackBarLayout.layoutParams = params
    val binding = SnackbarCustomLayoutBinding.inflate(LayoutInflater.from(this.context))


    binding.txtSnack.apply {
        text = mensaje
        if (!colorText.isNull())
            setTextColor(colorText!!)
    }
    if (!colorBackGround.isNull())
        binding.linear.setCardBackgroundColor(colorBackGround!!)
    if (!icono.isNull())
        binding.imgSnack.setImageDrawable(resources.getDrawable(icono!!, null))

    binding.txtAction.hide()

    snackBarLayout.addView(binding.root)

    if (conExplosion){
        snackbar.duration = Snackbar.LENGTH_INDEFINITE
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            explosionField.explode(binding.root)
            snackbar.dismiss()
        }, delayExplosion)
    }

    snackbar.show()
}


fun View.snackPlosionBarAction (
    mensaje: String,
    colorBackGround: Int?,
    icono: Int?,
    colorText: Int?,
    gravity: Int,
    textAction: String,
    colorTextAction: Int,
    listener: View.OnClickListener
)  {

    val explosionField = ExplosionField.attach2Window(this.context as Activity)


    val snackbar = Snackbar.make(this, "", Snackbar.LENGTH_INDEFINITE)
    snackbar.view.setBackgroundColor(Color.TRANSPARENT)

    val snackBarLayout = snackbar.view as Snackbar.SnackbarLayout
    snackBarLayout.removeAllViewsInLayout()
    snackBarLayout.setPadding(0,25,0,0)

    val params: FrameLayout.LayoutParams = snackBarLayout.layoutParams as FrameLayout.LayoutParams
    params.gravity = gravity

    when (gravity){
        Gravity.TOP -> {
            snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
            params.topMargin = 25
        }
        Gravity.CENTER -> snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        Gravity.BOTTOM -> snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
    }

    snackBarLayout.layoutParams = params
    val binding = SnackbarCustomLayoutBinding.inflate(LayoutInflater.from(this.context))

    binding.txtSnack.apply {
        text = mensaje
        if (!colorText.isNull())
            setTextColor(colorText!!)
    }
    if (!colorBackGround.isNull())
        binding.linear.setCardBackgroundColor(colorBackGround!!)
    if (!icono.isNull())
        binding.imgSnack.setImageDrawable(resources.getDrawable(icono!!, null))

    binding.txtAction.apply {
        text = textAction
        setTextColor(colorTextAction)
        setOnClickListener{
            explosionField.explode(binding.root)
            snackbar.dismiss()
            listener.onClick(it)
        }
    }

    binding.txtAction.visible()
    snackBarLayout.addView(binding.root)
    snackbar.show()
}


fun View.explotalo (temporizador: Long = 0) {
    Handler(Looper.getMainLooper()).postDelayed(Runnable {
        val bomba = ExplosionField.attach2Window(this.context as Activity)
        bomba.explode(this)
    }, temporizador)
    this.hide()
}