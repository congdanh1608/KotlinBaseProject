package com.danhtran.androidbaseproject.ui.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.danhtran.androidbaseproject.R
import com.danhtran.androidbaseproject.databinding.ActivityMainBinding
import com.danhtran.androidbaseproject.extras.Constant
import com.danhtran.androidbaseproject.ui.activity.BaseActivityViewModel
import com.danhtran.androidbaseproject.ui.activity.BaseAppCompatActivity
import com.orhanobut.logger.Logger

class MainActivity : BaseAppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null

    private val mainActivityVM: MainActivityVM by viewModels { MainActivityVMFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //load deep link when app is running
        onNewIntent(intent)
    }

    override fun loadPassedParamsIfNeeded(extras: Bundle) {
        super.loadPassedParamsIfNeeded(extras)
        //load deep link when app is not running

    }

    override fun setLayout(): Int {
        return R.layout.activity_main
    }

    override fun initUI() {
        mBinding = binding as ActivityMainBinding
    }

    override fun initViewModel(): BaseActivityViewModel? {
        mainActivityVM.countModel.observe(this, Observer<MainActivityVM.CountModel> { t ->
            Logger.d(t.count)
        })

        return mainActivityVM
    }

    override fun initData() {
        mBinding?.lifecycleOwner = this
        mBinding?.viewModel = mainActivityVM
    }

    override fun initListener() {

    }

    override fun onBackPressed() {
        val count = myFragmentManager!!.backStackEntryCount
        if (count <= 1) {
            exitApp()
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constant.REQUEST_CODE_RESULT_LOCATION_ON_OFF -> {
            }
            Constant.REQUEST_CODE_RESULT_LOCATION_PERMISSION -> {
            }
        }
    }

    //load deep link data onNewIntent()
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val action = intent.action
        val data = intent.dataString
        if (Intent.ACTION_VIEW == action && data != null) {
            val type = data.substring(data.lastIndexOf("/") + 1)
            if (!TextUtils.isEmpty(type)) {
                //do something
            }
        }
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
