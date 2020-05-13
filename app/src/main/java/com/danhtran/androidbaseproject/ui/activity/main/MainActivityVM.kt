package com.danhtran.androidbaseproject.ui.activity.main

import android.content.Context
import com.danhtran.androidbaseproject.MyApplication
import com.danhtran.androidbaseproject.ui.activity.BaseActivityViewModel

/**
 * Created by DanhTran on 5/11/2020.
 */

class MainActivityVM(val context: Context) : BaseActivityViewModel() {

    override fun initInject() {
        MyApplication.instance().appComponent.inject(this)
    }

    fun onClick() {

    }
}