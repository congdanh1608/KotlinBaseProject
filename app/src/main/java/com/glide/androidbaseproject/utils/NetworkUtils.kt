package com.glide.androidbaseproject.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by DanhTran on 7/23/2019.
 */
object NetworkUtils {
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
