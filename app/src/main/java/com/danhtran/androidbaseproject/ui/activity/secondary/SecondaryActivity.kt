package com.danhtran.androidbaseproject.ui.activity.secondary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.danhtran.androidbaseproject.R
import com.danhtran.androidbaseproject.databinding.ActivitySecondaryBinding
import com.danhtran.androidbaseproject.ui.activity.BaseActivityViewModel
import com.danhtran.androidbaseproject.ui.activity.BaseAppCompatActivity


/**
 * Created by DanhTran on 8/13/2019.
 */
class SecondaryActivity : BaseAppCompatActivity() {

    private var mBinding: ActivitySecondaryBinding? = null
    private lateinit var secondaryActivityVM: SecondaryActivityVM
    private lateinit var secondaryActivityVMFactory: SecondaryActivityVMFactory

    private var fragmentTag: String? = null
    private var bundle: Bundle? = null

    override fun loadPassedParamsIfNeeded(extras: Bundle) {
        super.loadPassedParamsIfNeeded(extras)

        fragmentTag = extras.getString(KEY_FRAGMENT_TAG)
        bundle = extras.get(KEY_FRAGMENT_BUNDLE) as Bundle?
    }

    override fun setLayout(): Int {
        return R.layout.activity_secondary
    }

    override fun initUI() {
        mBinding = binding as ActivitySecondaryBinding
    }

    override fun initViewModel(): BaseActivityViewModel? {
        secondaryActivityVMFactory = SecondaryActivityVMFactory()
        secondaryActivityVM = ViewModelProvider(this, secondaryActivityVMFactory).get(SecondaryActivityVM::class.java)

        return secondaryActivityVM
    }

    override fun initData() {
        mBinding?.lifecycleOwner = this
        mBinding?.viewModel = secondaryActivityVM

        //set fragment
        fragmentTag?.let {
            setFragment(it, bundle)
        }
    }

    override fun initListener() {

    }

    override fun onBackPressed() {
        val count = myFragmentManager!!.backStackEntryCount
        if (count <= 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        val KEY_FRAGMENT_TAG = "KEY_FRAGMENT_TAG"
        val KEY_FRAGMENT_BUNDLE = "KEY_FRAGMENT_BUNDLE"

        fun createIntent(context: Context): Intent {
            return Intent(context, SecondaryActivity::class.java)
        }
    }
}
