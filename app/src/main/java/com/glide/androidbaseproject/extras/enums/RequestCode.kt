package com.glide.androidbaseproject.extras.enums

import com.glide.androidbaseproject.utils.EnumUtils

import java.util.HashMap

/**
 * Created by danhtran on 09/03/2017.
 */
enum class RequestCode private constructor(val value: Int) {
    MENU_TOP(9989);


    companion object {
        private val map = HashMap<Int, RequestCode>()

        init {
            for (requestCode in RequestCode.values()) {
                map[requestCode.value] = requestCode
            }
        }

        fun fromValue(code: Int): RequestCode? {
            return EnumUtils.valueOf(code, map)
        }
    }
}
