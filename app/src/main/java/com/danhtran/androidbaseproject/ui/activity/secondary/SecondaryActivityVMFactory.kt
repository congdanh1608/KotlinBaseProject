package com.danhtran.androidbaseproject.ui.activity.secondary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * Created by DanhTran on 5/11/2020.
 */
class SecondaryActivityVMFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SecondaryActivityVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SecondaryActivityVM() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}