package es.icp.icp_commons.viewAnimations

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator

/*

$$\    $$\ $$$$$$\ $$$$$$$$\ $$\      $$\
$$ |   $$ |\_$$  _|$$  _____|$$ | $\  $$ |
$$ |   $$ |  $$ |  $$ |      $$ |$$$\ $$ |
\$$\  $$  |  $$ |  $$$$$\    $$ $$ $$\$$ |
 \$$\$$  /   $$ |  $$  __|   $$$$  _$$$$ |
  \$$$  /    $$ |  $$ |      $$$  / \$$$ |
   \$  /   $$$$$$\ $$$$$$$$\ $$  /   \$$ |
    \_/    \______|\________|\__/     \__|

 $$$$$$\  $$\   $$\ $$$$$$\ $$\      $$\  $$$$$$\ $$$$$$$$\  $$$$$$\  $$$$$$$\
$$  __$$\ $$$\  $$ |\_$$  _|$$$\    $$$ |$$  __$$\\__$$  __|$$  __$$\ $$  __$$\
$$ /  $$ |$$$$\ $$ |  $$ |  $$$$\  $$$$ |$$ /  $$ |  $$ |   $$ /  $$ |$$ |  $$ |
$$$$$$$$ |$$ $$\$$ |  $$ |  $$\$$\$$ $$ |$$$$$$$$ |  $$ |   $$ |  $$ |$$$$$$$  |
$$  __$$ |$$ \$$$$ |  $$ |  $$ \$$$  $$ |$$  __$$ |  $$ |   $$ |  $$ |$$  __$$<
$$ |  $$ |$$ |\$$$ |  $$ |  $$ |\$  /$$ |$$ |  $$ |  $$ |   $$ |  $$ |$$ |  $$ |
$$ |  $$ |$$ | \$$ |$$$$$$\ $$ | \_/ $$ |$$ |  $$ |  $$ |    $$$$$$  |$$ |  $$ |
\__|  \__|\__|  \__|\______|\__|     \__|\__|  \__|  \__|    \______/ \__|  \__|

*/

/**
 * Una serie de métodos de extensión de la clase view para añadir animaciones a las vistas de manera
 * Sencilla
 *
 * @author Gonzalo Racero, Carlos del Campo, Github copilot
 * @version 1.0
 *
 */

const val ANIMATION_DURATION = 1250L

fun View.rotateYForever(
    duration: Long = ANIMATION_DURATION,
    repeatCount: Int = ObjectAnimator.INFINITE,
) {
    val animation = ObjectAnimator.ofFloat(this, "rotationY", 0f, 360f)
    animation.duration = duration
    animation.repeatCount = repeatCount
    animation.interpolator = LinearInterpolator()
    animation.start()
}

fun View.rotateXForever(
    duration: Long = ANIMATION_DURATION,
    repeatCount: Int = ObjectAnimator.INFINITE,
) {
    val animation = ObjectAnimator.ofFloat(this, "rotationX", 0f, 360f)
    animation.duration = duration
    animation.repeatCount = repeatCount
    animation.interpolator = LinearInterpolator()
    animation.start()
}

fun View.visibleSmoothly(
    duration: Long = ANIMATION_DURATION
) {
    this.visibility = View.GONE
    this.visibility = View.VISIBLE
    this.alpha = 0f
    this.animate().alpha(1f).duration = duration
}

fun View.goneSmoothly(
    duration: Long = ANIMATION_DURATION
) {
    this.visibility = View.VISIBLE
    this.alpha = 1f
    this.animate().alpha(0f).duration = duration
    postDelayed(
        {
            this.visibility = View.GONE
        }, duration
    )
}

fun View.shakeAnimationXForever(
    duration: Long = ANIMATION_DURATION,
    repeatCount: Int = ObjectAnimator.INFINITE,
    oscilation: Float = 10f
) {
    val animation = ObjectAnimator.ofFloat(this, "translationX", oscilation, oscilation, -oscilation, oscilation, -oscilation, oscilation, -oscilation, oscilation, -oscilation, oscilation)
    animation.duration = duration
    animation.repeatCount = repeatCount
    animation.interpolator = LinearInterpolator()
    animation.start()
}

fun View.shakeAnimationYForever(
    duration: Long = ANIMATION_DURATION,
    repeatCount: Int = ObjectAnimator.INFINITE,
    oscilation: Float = 10f
) {
    val animation = ObjectAnimator.ofFloat(this, "translationY", oscilation, oscilation, -oscilation, oscilation, -oscilation, oscilation, -oscilation, oscilation, -oscilation, oscilation)
    animation.duration = duration
    animation.repeatCount = repeatCount
    animation.interpolator = LinearInterpolator()
    animation.start()
}