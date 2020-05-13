package com.danhtran.androidbaseproject.extras.enums

import java.util.*

/**
 * Created by danhtran on 09/03/2017.
 */
enum class RequestCode private constructor(val value: Int) {
    MENU_TOP(9989);


    companion object {
        private val map = HashMap<Int, RequestCode>()

        init {
            for (requestCode in values()) {
                map[requestCode.value] = requestCode
            }
        }

        fun fromValue(code: Int): RequestCode? {
            return if (map[code] != null) {
                map[code]
            } else map[0]
        }
    }
}
