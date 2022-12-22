package es.icp.icp_commons.Extensions

import android.animation.*
import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import es.icp.icp_commons.R
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException

/**
 * @author Carlos del Campo Cebrian
 * @version 1.0
 *
 */

fun Any?.isNull() = this == null

/**
 * Extension method to convert byteArray to String.
 */
private fun bytes2Hex(bts: ByteArray): String {
    var des = ""
    var tmp: String
    for (i in bts.indices) {
        tmp = Integer.toHexString(bts[i].toInt() and 0xFF)
        if (tmp.length == 1) {
            des += "0"
        }
        des += tmp
    }
    return des
}
/**
* Extension method to get base64 string for Bitmap.
*/
fun Bitmap.toBase64(): String {
    var result = ""
    val baos = ByteArrayOutputStream()
    try {
        compress(Bitmap.CompressFormat.JPEG, 100, baos)
        baos.flush()
        baos.close()
        val bitmapBytes = baos.toByteArray()
        result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            baos.flush()
            baos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return result
}

fun imagenBase64 (fichero: String) : String {

    var fis: FileInputStream? = null
    var base64String: String? = ""
    val bos = ByteArrayOutputStream()
    try {
        fis = FileInputStream(fichero.replace("file:///", ""))
        val buffer = ByteArray(1024 * 100)
        var count = 0
        while (fis.read(buffer).also { count = it } != -1) {
            bos.write(buffer, 0, count)
        }
        fis.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    base64String = Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT)
    return base64String

}


/**
 * Extension method to provide handler and mainThread.
 */
private object ContextHandler {
    val handler = Handler(Looper.getMainLooper())
    val mainThread = Looper.getMainLooper().thread
}
/**
 * Extension method to run block of code on UI Thread.
 */
fun runOnUiThread(action: () -> Unit){
    if (ContextHandler.mainThread == Thread.currentThread()) action() else ContextHandler.handler.post { action() }
}



fun ImageView.loadImage(url: String?, placeHolder: Int = -1) {
    if (url?.isEmpty() == true) return

    if(placeHolder != -1)
        Glide.with(this).load(url).placeholder(placeHolder).into(this)
    else Glide.with(this).load(url).into(this)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show(show: Boolean){
    if (show) visible() else hide()
}

fun View.pulseAnimate(count: Int = 3) {
    val scaleDown: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
        this,
        PropertyValuesHolder.ofFloat("scaleX", 1.2f),
        PropertyValuesHolder.ofFloat("scaleY", 1.2f)
    )
    scaleDown.duration = 310

    scaleDown.repeatCount = count
    scaleDown.repeatMode = ObjectAnimator.REVERSE

    scaleDown.start()
}

fun View.setBgColorAnim(colorFrom: Int, colorTo: Int, duration: Long = 500): ValueAnimator {
    val view = this
    val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
    colorAnimation.duration = duration
    colorAnimation.addUpdateListener { animator -> view.setBackgroundColor(animator.animatedValue as Int) }
    colorAnimation.start()
    return colorAnimation
}


fun View.goneWithFade(duration: Long = 300) {
    val view = this
    view.animate()
        .alpha(0f)
        .setDuration(duration)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                view.visibility = View.GONE
            }
        })
}

fun View.visbleWithFade(duration: Long = 300) {
    val view = this
    view.alpha = 0f
    view.visible()
    view.animate()
        .alpha(1f)
        .setDuration(duration)
        .setListener(null)
}

fun View.slideDown() {
    // Prepare the View for the animation
    this.visibility = View.VISIBLE;
    this.alpha = 0.0f;

// Start the animation
    this.animate()
        .translationY(this.height.toFloat())
        .alpha(1.0f)
        .setListener(null);
}

fun View.slideUp() {
    val view = this
    view.animate()
        .translationY(0.0f)
        .alpha(0.0f)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                view.visibility = View.VISIBLE;
            }
        })
}

fun View.changeBackgroundColorWithAnim(
    fromColor: Int,
    toColor: Int
) {
    val view = this
    val valueAnimator =
        ValueAnimator.ofObject(ArgbEvaluator(), fromColor, toColor)
    valueAnimator.duration = 700
    valueAnimator.addUpdateListener { animation -> view.setBackgroundColor((animation.animatedValue as Int)) }
    valueAnimator.start()
}

fun TextInputEditText.texto(texto: String){
    this.text?.clear()
    this.text?.append(texto)
}



