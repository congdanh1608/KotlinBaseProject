package com.danhtran.androidbaseproject.extras.listener

interface SingleResultListener<T : Any> {
    fun onSuccess(data: T)

    fun onFailure(error: Any)
}
