package com.danhtran.androidbaseproject.ui.activity.splash

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.danhtran.androidbaseproject.MyApplication
import com.danhtran.androidbaseproject.extras.enums.HawkKey
import com.danhtran.androidbaseproject.ui.BaseViewModel
import com.danhtran.androidbaseproject.ui.activity.BaseAppCompatActivity
import com.danhtran.androidbaseproject.ui.activity.main.MainActivity
import com.danhtran.androidbaseproject.ui.activity.tour.TourActivity
import com.orhanobut.hawk.Hawk


/**
 * Created by DanhTran on 8/13/2019.
 */
class SplashActivity : BaseAppCompatActivity() {
    private val DELAY_TIME = 1000

    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        MyApplication.instance().appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(this.createSplashLayout());
    }

    override fun setLayout(): Int {
        return 0
    }

    override fun initUI() {

    }

    override fun initViewModel(): BaseViewModel? {
        return null
    }

    override fun initData() {
        handler = Handler()
        handler?.postDelayed({
            if (!Hawk.get(HawkKey.IS_NOT_FIRST_VIEW.value, false)) {
                startActivityAsRoot(TourActivity::class.java.name, null)
            } else {
                startActivityAsRoot(MainActivity::class.java.name, null)
            }
        }, DELAY_TIME.toLong())
    }

    override fun initListener() {

    }

    fun createSplashLayout(): View? {
        val transparentView = View(this)
        transparentView.setBackgroundColor(Color.TRANSPARENT)
        return transparentView
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }
}
