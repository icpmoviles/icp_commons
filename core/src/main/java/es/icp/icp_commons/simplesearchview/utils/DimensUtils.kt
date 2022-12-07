package es.icp.icp_commons.simplesearchview.utils

import android.content.Context
import android.util.TypedValue
import kotlin.math.roundToInt

/**
 * @author Carlos del Campo Cebrian
 * @version 1.0
 *
 */
object DimensUtils {
    @JvmStatic
    fun convertDpToPx(dp: Int, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).roundToInt()
    }

    @JvmStatic
    fun convertDpToPx(dp: Float, context: Context): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    }
}