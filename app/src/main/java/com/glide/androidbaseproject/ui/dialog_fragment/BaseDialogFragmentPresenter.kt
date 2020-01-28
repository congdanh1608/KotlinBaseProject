package com.glide.androidbaseproject.ui.dialog_fragment

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by DanhTran on 6/10/2019.
 */
abstract class BaseDialogFragmentPresenter {
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
