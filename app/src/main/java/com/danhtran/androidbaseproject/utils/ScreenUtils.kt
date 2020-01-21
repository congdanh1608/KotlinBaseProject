package com.danhtran.androidbaseproject.utils

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager
import com.danhtran.androidbaseproject.MyApplication

/**
 * Created by danhtran on 2/28/2018.
 */

object ScreenUtils {
    private val SDK_VERSION = Build.VERSION.SDK_INT
    private var DEVICE_WIDTH = 0
    private var DEVICE_HEIGHT = 0

    /**
     * Get width of screen in PX
     *
     * @return width
     */
    val widthScreenInPX: Int
        get() {
            getScreenSize()
            return DEVICE_WIDTH
        }

    /**
     * Get height of screen in PX
     *
     * @return height
     */
    val heightScreenInPX: Int
        get() {
            getScreenSize()
            return DEVICE_HEIGHT
        }

    /**
     * Get width of screen in DP
     *
     * @return width
     */
    val widthScreenInDP: Int
        get() {
            getScreenSize()
            return SizeUtils.pxToDp(DEVICE_WIDTH)
        }

    /**
     * Get height of screen in DP
     *
     * @return height
     */
    val heightScreenInDP: Int
        get() {
            getScreenSize()
            return SizeUtils.pxToDp(DEVICE_HEIGHT)
        }

    private fun getScreenSize() {
        if (DEVICE_WIDTH != 0 && DEVICE_HEIGHT != 0) {
            return
        }

        val windowManager =
            MyApplication.instance().applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (windowManager != null) {
            val display = windowManager.defaultDisplay

            val metrics = DisplayMetrics()
            display.getMetrics(metrics)
            var widthPixels = metrics.widthPixels
            var heightPixels = metrics.heightPixels

            if (SDK_VERSION >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && SDK_VERSION < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                try {
                    widthPixels = Display::class.java.getMethod("getWidth")
                        .invoke(display) as Int
                    heightPixels = Display::class.java.getMethod("getHeight")
                        .invoke(display) as Int

                } catch (exp: Exception) {
                    exp.printStackTrace()
                }

            } else if (SDK_VERSION >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                try {
                    val realSize = Point()
                    Display::class.java.getMethod("getSize", Point::class.java).invoke(
                        display,
                        realSize
                    )
                    widthPixels = realSize.x
                    heightPixels = realSize.y
                } catch (exp: Exception) {
                    exp.printStackTrace()
                }

            } else {
                widthPixels = display.width
                heightPixels = display.height
            }// includes window decorations (statusbar bar/menu bar)
            DEVICE_WIDTH = widthPixels
            DEVICE_HEIGHT = heightPixels
        }
    }
}
