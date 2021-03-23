package com.danhtran.androidbaseproject.ui

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import com.danhtran.androidbaseproject.extras.LiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * Created by DanhTran on 5/31/2019.
 */
abstract class BaseViewModel : ViewModel(), Observable {
    protected var disposable: Disposable
    protected var disposable1: Disposable
    protected var disposable2: Disposable
    protected var disposable3: Disposable

    val progressState = LiveEvent<Boolean>()
    val errorHandler = LiveEvent<Throwable>()

    abstract fun initInject()

    init {
        this.disposable = CompositeDisposable()
        this.disposable1 = CompositeDisposable()
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

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }
}

