package com.danhtran.androidbaseproject.ui.activity.full

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import com.danhtran.androidbaseproject.R
import com.danhtran.androidbaseproject.databinding.ActivityFullScreenBinding
import com.danhtran.androidbaseproject.ui.activity.BaseActivityViewModel
import com.danhtran.androidbaseproject.ui.activity.BaseAppCompatActivity

/**
 * Created by DanhTran on 8/13/2019.
 */
class FullScreenActivity : BaseAppCompatActivity() {

    private var mBinding: ActivityFullScreenBinding? = null
    private val fullActivityVM: FullActivityVM by viewModels { FullActivityVMFactory() }

    var fragmentTag: String? = null
    private var bundle: Bundle? = null


    override fun loadPassedParamsIfNeeded(extras: Bundle) {
        super.loadPassedParamsIfNeeded(extras)

        fragmentTag = extras.getString(KEY_FRAGMENT_TAG)
        bundle = extras.getParcelable(KEY_FRAGMENT_BUNDLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        super.onCreate(savedInstanceState)
    }

    override fun setLayout(): Int {
        return R.layout.activity_full_screen
    }

    override fun initUI() {
        mBinding = binding as ActivityFullScreenBinding
    }

    override fun initViewModel(): BaseActivityViewModel? {
        return fullActivityVM
    }

    override fun initData() {
        mBinding?.lifecycleOwner = this
        mBinding?.viewModel = fullActivityVM

        //set fragment
        fragmentTag?.let {
            setFragment(it, bundle)
        }
    }

    override fun initListener() {

    }

    override fun onBackPressed() {
        val count = myFragmentManager?.backStackEntryCount ?: 0
        if (count <= 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    }

    companion object {
        val KEY_FRAGMENT_TAG = "KEY_FRAGMENT_TAG"
        val KEY_FRAGMENT_BUNDLE = "KEY_FRAGMENT_BUNDLE"

        fun createIntent(context: Context): Intent {
            return Intent(context, FullScreenActivity::class.java)
        }
    }
}
