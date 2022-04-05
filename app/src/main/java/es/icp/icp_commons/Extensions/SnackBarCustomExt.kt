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
import es.icp.icp_commons.databinding.SnackbarCustomLayoutBinding
import tyrantgit.explosionfield.ExplosionField


/**
 * Crea una snackbar personalizada
 * Params:
 *       mensaje -> Texto a mostrar
 *       colorBackGround -> Color de fondo, si es null se usa gris
 *       colorText -> color del texto, si se pasa null se usa white
 *       conExplosion -> booleano para indicar si queremos animar con una explosion la salida del snack. True -> si / False -> no
 */
fun View.snackPlosionBar (mensaje: String, colorBackGround: Int?, icono: Int?, colorText: Int?, conExplosion: Boolean)  {

    val explosionField = ExplosionField.attach2Window(this.context as Activity)


    val snackbar = Snackbar.make(this, "", Snackbar.LENGTH_LONG)
    snackbar.view.setBackgroundColor(Color.TRANSPARENT)
    snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE

    val snackBarLayout = snackbar.view as Snackbar.SnackbarLayout
    snackBarLayout.removeAllViewsInLayout()
    snackBarLayout.setPadding(0,25,0,0)

    val params: FrameLayout.LayoutParams = snackBarLayout.layoutParams as FrameLayout.LayoutParams
    params.gravity = Gravity.TOP
    params.topMargin = 25
    snackBarLayout.layoutParams = params
    val binding = SnackbarCustomLayoutBinding.inflate(LayoutInflater.from(this.context))


    binding.txtSnack.apply {
        text = mensaje
        if (!colorText.isNull())
            setTextColor(colorText!!)
    }
    if (!colorBackGround.isNull())
        binding.linear.setCardBackgroundColor(colorBackGround!!)
    if (!icono.isNull()) binding.imgSanck.setImageDrawable(resources.getDrawable(icono!!, null))

    snackBarLayout.addView(binding.root)

    if (conExplosion){
        snackbar.duration = Snackbar.LENGTH_INDEFINITE
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            explosionField.explode(binding.root)
        }, 3000)
    }

    snackbar.show()
}
