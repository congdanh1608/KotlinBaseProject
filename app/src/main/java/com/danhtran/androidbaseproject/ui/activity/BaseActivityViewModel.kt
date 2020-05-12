package com.danhtran.androidbaseproject.ui.activity

import androidx.lifecycle.ViewModel
import com.danhtran.androidbaseproject.extras.LiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * Created by DanhTran on 5/31/2019.
 */
abstract class BaseActivityViewModel : ViewModel() {
    protected var disposable: Disposable
    protected var disposable1: Disposable
    protected var disposable2: Disposable

    val progressState = LiveEvent<Boolean>()

    abstract fun initInject()

    init {
        this.disposable = CompositeDisposable()
        this.disposable1 = CompositeDisposable()
        this.disposable2 = CompositeDisposable()

        initInject()
    }

    protected fun showProgress() {
        progressState.postValue(true)
    }

    protected fun hideProgress() {
        progressState.postValue(false)
    }
}

