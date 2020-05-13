package com.danhtran.androidbaseproject.extras.enums

import java.util.*

/**
 * Created by SilverWolf on 09/03/2017.
 */
enum class AppTheme(val value: Int) {
    DARK(0),
    LIGHT(1),

    ;


    companion object {
        private val map = HashMap<Int, AppTheme>()

        init {
            for (appColor in values()) {
                map[appColor.value] = appColor
            }
        }

        fun fromValue(code: Int): AppTheme? {
            return if (map[code] != null) {
                map[code]
            } else map[0]
        }
    }
}
