package com.danhtran.androidbaseproject.ui.activity.full

import android.os.Bundle
import com.danhtran.androidbaseproject.ui.activity.BaseActivityPresenter

/**
 * Created by DanhTran on 8/13/2019.
 */
class FullScreenActivityPresenter(
    private val listener: FullScreenActivityListener,
    bundle: Bundle?
) :
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
