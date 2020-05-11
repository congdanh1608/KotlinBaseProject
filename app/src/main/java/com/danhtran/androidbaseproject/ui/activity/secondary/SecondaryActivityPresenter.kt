package com.danhtran.androidbaseproject.ui.activity.secondary

import android.os.Bundle
import com.danhtran.androidbaseproject.ui.activity.BaseActivityPresenter

/**
 * Created by DanhTran on 8/13/2019.
 */
class SecondaryActivityPresenter(private val listener: SecondaryActivityListener, bundle: Bundle?) :
    BaseActivityPresenter() {

    init {
        initFragment(bundle)
    }

    override fun initInject() {

    }

    private fun initFragment(bundle: Bundle?) {
        val tagFragment = listener.fragmentTag
        tagFragment?.let {
            listener.baseActivity.setFragment(tagFragment, bundle)
        }
    }
}
