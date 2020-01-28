package com.glide.androidbaseproject.ui.activity

/**
 * Created by DanhTran on 8/13/2019.
 */
interface BaseActivityListener {

    val baseActivity: BaseAppCompatActivity
    fun showProgress()

    fun hideProgress()
}
