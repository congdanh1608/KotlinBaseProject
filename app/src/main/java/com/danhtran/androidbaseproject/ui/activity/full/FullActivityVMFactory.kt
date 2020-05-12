package com.danhtran.androidbaseproject.ui.activity.full

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * Created by DanhTran on 5/11/2020.
 */
class FullActivityVMFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FullActivityVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FullActivityVM() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}