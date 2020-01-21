package com.danhtran.androidbaseproject.appmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by danhtran on 2/11/2018.
 */

class Movie {
    @SerializedName("albumId")
    @Expose
    var albumId: Int? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("title")
    @Expose
    lateinit var title: String
    @SerializedName("url")
    @Expose
    lateinit var url: String
    @SerializedName("thumbnailUrl")
    @Expose
    lateinit var thumbnailUrl: String
}
