package com.danhtran.androidbaseproject.ui.activity.tour

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * Created by DanhTran on 5/11/2020.
 */
class TourActivityVMFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TourActivityVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TourActivityVM() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}