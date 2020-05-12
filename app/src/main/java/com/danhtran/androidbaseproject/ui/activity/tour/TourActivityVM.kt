package com.danhtran.androidbaseproject.ui.activity.tour

import android.view.View
import com.danhtran.androidbaseproject.R
import com.danhtran.androidbaseproject.extras.LiveEvent
import com.danhtran.androidbaseproject.ui.activity.BaseActivityViewModel

/**
 * Created by DanhTran on 5/12/2020.
 */
class TourActivityVM() : BaseActivityViewModel() {
    val doneAction = LiveEvent<Boolean>()
    val skipAction = LiveEvent<Boolean>()

    override fun initInject() {

    }

    fun onClickListener(): View.OnClickListener {
        return View.OnClickListener { v: View? ->
            when (v?.id) {
                R.id.btnDone -> doneAction.value = true
                R.id.btnSkip -> skipAction.value = true
            }
        }
    }
}