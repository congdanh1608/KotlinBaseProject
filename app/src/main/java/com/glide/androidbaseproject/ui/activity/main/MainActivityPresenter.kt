package com.glide.androidbaseproject.ui.activity.main

import android.util.Log
import android.view.View
import com.glide.androidbaseproject.MyApplication
import com.glide.androidbaseproject.graphql.services.AuthenticationService
import com.glide.androidbaseproject.ui.activity.BaseActivityPresenter
import javax.inject.Inject

/**
 * Created by danhtran on 2/25/2018.
 */

class MainActivityPresenter(private val listener: MainActivityListener) : BaseActivityPresenter() {
    @Inject
    internal lateinit var authenticationService: AuthenticationService

    override fun initInject() {
        MyApplication.instance().appComponent.inject(this)
    }

    init {
        getLanguages()
    }

    val image: String
        get() = "https://i.amz.mshcdn.com/FZXQWbpZ9hwycnowQwjI7zjejxk=/950x534/filters:quality(90)/https%3A%2F%2Fblueprint-api-production.s3.amazonaws.com%2Fuploads%2Fcard%2Fimage%2F528403%2F153ae5f3-17fa-4620-b8b1-33cd0d354385.jpg"

    fun onClickListener(index: Int): View.OnClickListener {
        return View.OnClickListener { }
    }

    private fun getLanguages() {
        disposable = authenticationService.getLanguages()
            .subscribe(
                {
                    Log.d("Result", "Success")
                },
                {
                    Log.d("Result", "fail")
                })
    }
}
