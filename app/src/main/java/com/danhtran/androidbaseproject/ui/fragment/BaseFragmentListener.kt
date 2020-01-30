package com.danhtran.androidbaseproject.ui.fragment

import com.danhtran.androidbaseproject.ui.activity.BaseAppCompatActivity

/**
 * Created by DanhTran on 8/13/2019.
 */
interface BaseFragmentListener {

    val baseActivity: BaseAppCompatActivity

    val baseFragment: BaseFragment
    fun showProgress()

    fun hideProgress()
}
