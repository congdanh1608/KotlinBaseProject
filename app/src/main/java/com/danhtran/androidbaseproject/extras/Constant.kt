package com.danhtran.androidbaseproject.extras


import com.danhtran.androidbaseproject.MyApplication

/**
 * Created by DanhTran on 6/2/2019.
 */
object Constant {
    val DATABASE_NAME = MyApplication.instance().packageName + "_database"
    val TERM = "https://mypsychicapp.com/#/tos"
    val PRIVACY = "https://mypsychicapp.com/#/tos"

    val REQUEST_CODE_RESULT_LOCATION_PERMISSION = 1000
    val REQUEST_CODE_RESULT_LOCATION_ON_OFF = 1001
}
