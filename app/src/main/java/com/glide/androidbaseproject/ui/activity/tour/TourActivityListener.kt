package com.glide.androidbaseproject.ui.activity.tour

import com.glide.androidbaseproject.ui.activity.BaseActivityListener

/**
 * Created by DanhTran on 8/13/2019.
 */
interface TourActivityListener : BaseActivityListener {
    fun moveNextTour()

    fun launchHomeScreen()
}
