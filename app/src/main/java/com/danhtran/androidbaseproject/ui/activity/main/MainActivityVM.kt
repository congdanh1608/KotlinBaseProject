package com.danhtran.androidbaseproject.ui.activity.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.danhtran.androidbaseproject.MyApplication
import com.danhtran.androidbaseproject.graphql.services.AuthenticationService
import com.danhtran.androidbaseproject.serviceAPI.extras.RxScheduler
import com.danhtran.androidbaseproject.ui.activity.BaseActivityViewModel
import com.orhanobut.logger.Logger
import javax.inject.Inject

/**
 * Created by DanhTran on 5/11/2020.
 */

class MainActivityVM(val context: Context) : BaseActivityViewModel() {
    @Inject
    lateinit var authorizationService: AuthenticationService


    var image =
        MutableLiveData("https://i.amz.mshcdn.com/FZXQWbpZ9hwycnowQwjI7zjejxk=/950x534/filters:quality(90)/https%3A%2F%2Fblueprint-api-production.s3.amazonaws.com%2Fuploads%2Fcard%2Fimage%2F528403%2F153ae5f3-17fa-4620-b8b1-33cd0d354385.jpg")

    override fun initInject() {
        MyApplication.instance().appComponent.inject(this)
    }

    fun onClick() {
        RxScheduler.onStop(disposable)
        disposable = authorizationService.getLanguages().subscribe({
            Logger.d(it.data().toString())
        }, {
            showError(it)
        })
    }
}