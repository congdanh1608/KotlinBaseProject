package com.glide.androidbaseproject.ui.activity.tour

import android.view.View
import com.glide.androidbaseproject.MyApplication
import com.glide.androidbaseproject.R
import com.glide.androidbaseproject.database.share_preferences.SharedPrefsHelper
import com.glide.androidbaseproject.extras.enums.SharePrefs
import com.glide.androidbaseproject.ui.activity.BaseActivityPresenter

import javax.inject.Inject

/**
 * Created by DanhTran on 8/13/2019.
 */
class TourActivityPresenter(private val listener: TourActivityListener) : BaseActivityPresenter() {
    @Inject
    internal lateinit var sharedPrefsHelper: SharedPrefsHelper

    override fun initInject() {
        MyApplication.instance().appComponent.inject(this)
    }

    internal fun saveFlag() {
        //save into share preference
        sharedPrefsHelper.writeBoolean(SharePrefs.IS_NOT_FIRST_VIEW.value, true)
    }

    fun onClickListener(): View.OnClickListener {
        return View.OnClickListener { v: View? ->
            when (v?.id) {
                R.id.btnDone -> listener.moveNextTour()
                R.id.btnSkip -> listener.launchHomeScreen()
            }
        }
    }
}
