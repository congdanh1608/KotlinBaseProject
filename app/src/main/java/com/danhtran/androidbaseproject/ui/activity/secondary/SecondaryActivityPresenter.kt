package com.danhtran.androidbaseproject.ui.activity.secondary

import com.danhtran.androidbaseproject.ui.activity.BaseActivityPresenter

/**
 * Created by DanhTran on 8/13/2019.
 */
class SecondaryActivityPresenter(private val listener: SecondaryActivityListener, bundle: Any) :
    BaseActivityPresenter() {

    init {

        initFragment(bundle)
    }

    override fun initInject() {

    }

    private fun initFragment(bundle: Any) {
        val tagFragment = listener.fragmentTag
        if (tagFragment != null) {
            listener.baseActivity.setFragment(tagFragment, bundle)
        }
    }
}
