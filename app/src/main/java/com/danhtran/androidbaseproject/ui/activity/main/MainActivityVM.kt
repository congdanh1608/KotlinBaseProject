package com.danhtran.androidbaseproject.ui.activity.main

import android.content.Context
import com.danhtran.androidbaseproject.MyApplication
import com.danhtran.androidbaseproject.ui.BaseViewModel

/**
 * Created by DanhTran on 5/11/2020.
 */

class MainActivityVM(val context: Context) : BaseViewModel() {

    override fun initInject() {
        MyApplication.instance().appComponent.inject(this)
    }

    fun onClick() {

    }
}