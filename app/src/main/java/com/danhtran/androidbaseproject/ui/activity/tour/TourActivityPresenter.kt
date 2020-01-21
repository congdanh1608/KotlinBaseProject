package com.danhtran.androidbaseproject.ui.activity.tour

import android.view.View
import com.danhtran.androidbaseproject.MyApplication
import com.danhtran.androidbaseproject.R
import com.danhtran.androidbaseproject.database.share_preferences.SharedPrefsHelper
import com.danhtran.androidbaseproject.extras.enums.SharePrefs
import com.danhtran.androidbaseproject.ui.activity.BaseActivityPresenter

import javax.inject.Inject

/**
 * Created by DanhTran on 8/13/2019.
 */
class TourActivityPresenter(private val listener: TourActivityListener) : BaseActivityPresenter() {
    @Inject
    internal var sharedPrefsHelper: SharedPrefsHelper? = null

    override fun initInject() {
        MyApplication.instance().appComponent!!.inject(this)
    }

    internal fun saveFlag() {
        //save into share preference
        sharedPrefsHelper!!.writeBoolean(SharePrefs.IS_NOT_FIRST_VIEW.value, true)
    }

    fun onClickListener(): View.OnClickListener {
        return { v ->
            when (v.getId()) {
                R.id.btnDone -> listener.moveNextTour()
                R.id.btnSkip -> listener.launchHomeScreen()
            }
        }
    }
}
