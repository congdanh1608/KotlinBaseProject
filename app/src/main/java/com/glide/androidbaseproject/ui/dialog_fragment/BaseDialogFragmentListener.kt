package com.glide.androidbaseproject.ui.dialog_fragment

import com.glide.androidbaseproject.ui.activity.BaseAppCompatActivity
import com.glide.androidbaseproject.ui.fragment.BaseFragment

/**
 * Created by DanhTran on 8/13/2019.
 */
interface BaseDialogFragmentListener {

    val baseActivity: BaseAppCompatActivity

    val baseFragment: BaseFragment

    val baseDialogFragment: BaseDialogFragment
    fun showProgress()

    fun hideProgress()
}
