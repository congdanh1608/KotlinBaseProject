package com.danhtran.androidbaseproject.utils


import com.danhtran.androidbaseproject.MyApplication

/**
 * Created by danhtran on 07/07/2016.
 */

//Performance calculate convert dp, px.
object SizeUtils {
    private var isInitialised = false
    private var pixelsPerOneDp: Float = 0.toFloat()

    private fun initialise() {
        pixelsPerOneDp = MyApplication.instance().applicationContext
            .resources.displayMetrics.densityDpi / 160f
        isInitialised = true
    }

    /**
     * Convert PX to DP
     *
     * @param px px
     * @return dp
     */
    fun pxToDp(px: Int): Int {
        if (!isInitialised) {
            initialise()
        }
        return (px / pixelsPerOneDp).toInt()
    }

    /**
     * Convert DP to PX
     *
     * @param dp dp
     * @return px
     */
    fun dpToPx(dp: Int): Int {
        if (!isInitialised) {
            initialise()
        }
        return (dp * pixelsPerOneDp).toInt()
    }
}
