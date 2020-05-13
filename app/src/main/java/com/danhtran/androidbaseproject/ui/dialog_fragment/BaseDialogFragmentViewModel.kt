package com.danhtran.androidbaseproject.ui.dialog_fragment

import androidx.lifecycle.ViewModel
import com.danhtran.androidbaseproject.extras.LiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by DanhTran on 5/13/2020.
 */
abstract class BaseDialogFragmentViewModel : ViewModel() {
    protected val disposable: Disposable
    protected val disposable2: Disposable
    protected val disposable3: Disposable

    val progressState = LiveEvent<Boolean>()
    val errorHandler = LiveEvent<Throwable>()

    abstract fun initInject()

    init {
        this.disposable = CompositeDisposable()
        this.disposable2 = CompositeDisposable()
        this.disposable3 = CompositeDisposable()

        initInject()
    }

    protected fun showError(throwable: Throwable) {
        errorHandler.postValue(throwable)
    }

    protected fun showProgress() {
        progressState.postValue(true)
    }

    protected fun hideProgress() {
        progressState.postValue(false)
    }
}