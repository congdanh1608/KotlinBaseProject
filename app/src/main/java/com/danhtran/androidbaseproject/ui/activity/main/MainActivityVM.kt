package com.danhtran.androidbaseproject.ui.activity.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.danhtran.androidbaseproject.ui.activity.BaseActivityViewModel

/**
 * Created by DanhTran on 5/11/2020.
 */

class MainActivityVM(val context: Context) : BaseActivityViewModel() {

    var countModel = MutableLiveData(CountModel())
    var image =
        MutableLiveData("https://i.amz.mshcdn.com/FZXQWbpZ9hwycnowQwjI7zjejxk=/950x534/filters:quality(90)/https%3A%2F%2Fblueprint-api-production.s3.amazonaws.com%2Fuploads%2Fcard%2Fimage%2F528403%2F153ae5f3-17fa-4620-b8b1-33cd0d354385.jpg")

    override fun initInject() {

    }

    public fun onClick() {
        increase()
    }

    private fun increase() {
        val count = countModel.value
        count?.count = (count?.count ?: 0) + 1
        countModel.postValue(count)
    }

    class CountModel {
        var count = 0
    }
}