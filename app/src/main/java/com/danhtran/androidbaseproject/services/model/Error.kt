package com.danhtran.androidbaseproject.services.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by DanhTran on 5/31/2019.
 */
class Error {

    @SerializedName("errorMessage")
    @Expose
    var errorMessage: String? = null
    @SerializedName("errorCode")
    @Expose
    var errorCode: Int? = null
}
