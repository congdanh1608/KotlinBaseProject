package com.danhtran.androidbaseproject.ui.activity

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by DanhTran on 5/31/2019.
 */
abstract class BaseActivityPresenter {
    protected var disposable: Disposable
    protected var disposable2: Disposable
    protected var disposable3: Disposable

    abstract fun initInject()

    init {
        this.disposable = CompositeDisposable()
        this.disposable2 = CompositeDisposable()
        this.disposable3 = CompositeDisposable()

        initInject()
    }
}

