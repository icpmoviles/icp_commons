package es.icp.icp_commons.simplesearchview.utils

import android.view.View

/**
 * @author Carlos del Campo Cebrian
 * @version 1.0
 *
 */
abstract class SimpleAnimationListener : SimpleAnimationUtils.AnimationListener {
    override fun onAnimationStart(view: View): Boolean {
        // No action
        return false
    }

    override fun onAnimationEnd(view: View): Boolean {
        // No action
        return false
    }

    override fun onAnimationCancel(view: View): Boolean {
        // No action
        return false
    }
}