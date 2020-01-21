package com.danhtran.androidbaseproject.extras.listener

interface MultiResultListener<T : Any> {
    fun onSuccess(data: List<T>)

    fun onFailure(error: Any)
}
