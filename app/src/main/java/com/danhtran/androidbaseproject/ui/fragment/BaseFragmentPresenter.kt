package com.danhtran.androidbaseproject.ui.fragment

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by DanhTran on 5/31/2019.
 */
abstract class BaseFragmentPresenter {
    private val disposable: Disposable
    private val disposable2: Disposable
    private val disposable3: Disposable

    abstract fun initInject()

    init {
        this.disposable = CompositeDisposable()
        this.disposable2 = CompositeDisposable()
        this.disposable3 = CompositeDisposable()

        initInject()
    }
}
