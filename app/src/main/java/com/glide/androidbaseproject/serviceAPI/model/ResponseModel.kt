package com.glide.androidbaseproject.serviceAPI.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by DanhTran on 5/31/2019.
 */
class ResponseModel<T> {
    @SerializedName("data")
    @Expose
    private var data: T? = null
    @SerializedName("errors")
    @Expose
    var errors: List<Error>? = null

    fun getData(): Any? {
        return data
    }

    fun setData(data: T) {
        this.data = data
    }
}
