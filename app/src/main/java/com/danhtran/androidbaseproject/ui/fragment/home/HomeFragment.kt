package com.danhtran.androidbaseproject.ui.fragment.home

import androidx.fragment.app.viewModels
import com.danhtran.androidbaseproject.R
import com.danhtran.androidbaseproject.databinding.FragmentHomeBinding
import com.danhtran.androidbaseproject.ui.fragment.BaseFragment
import com.danhtran.androidbaseproject.ui.fragment.BaseFragmentViewModel

/**
 * Created by DanhTran on 5/13/2020.
 */
class HomeFragment : BaseFragment() {
    private lateinit var mBinding: FragmentHomeBinding
    private val homeFragmentVM: HomeFragmentVM by viewModels { HomeFragmentVMFactory() }


    override fun setLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initUI() {
        mBinding = binding as FragmentHomeBinding
    }

    override fun initViewModel(): BaseFragmentViewModel? {
        return homeFragmentVM
    }

    override fun initData() {
        mBinding.viewModel = homeFragmentVM
        mBinding.lifecycleOwner = this
    }

    override fun initListener() {

    }

    override fun onConfigurationChanged() {

    }
}