package com.glide.androidbaseproject.ui.activity.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.glide.androidbaseproject.MyApplication
import com.glide.androidbaseproject.database.share_preferences.SharedPrefsHelper
import com.glide.androidbaseproject.extras.enums.SharePrefs
import com.glide.androidbaseproject.ui.activity.BaseAppCompatActivity
import com.glide.androidbaseproject.ui.activity.main.MainActivity
import com.glide.androidbaseproject.ui.activity.tour.TourActivity

import javax.inject.Inject

/**
 * Created by DanhTran on 8/13/2019.
 */
class SplashActivity : BaseAppCompatActivity() {
    private val DELAY_TIME = 1000

    @Inject
    internal lateinit var sharedPrefsHelper: SharedPrefsHelper

    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        MyApplication.instance().appComponent.inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun setLayout(): Int {
        return 0
    }

    override fun initUI() {

    }

    override fun initData() {
        handler = Handler()
        handler!!.postDelayed({
            if (!sharedPrefsHelper.readBoolean(SharePrefs.IS_NOT_FIRST_VIEW.value)!!) {
                startActivityAsRoot(TourActivity::class.java.name, null)
            } else {
                startActivityAsRoot(MainActivity::class.java.name, null)
            }
        }, DELAY_TIME.toLong())
    }

    override fun initListener() {

    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }
}
