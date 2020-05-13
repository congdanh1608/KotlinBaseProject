package com.danhtran.androidbaseproject.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by DanhTran on 5/11/2020.
 */
class HomeFragmentVMFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return HomeFragmentVM() as T
    }
}