package com.danhtran.androidbaseproject.extras.enums

import java.util.HashMap

/**
 * Created by danhtran on 09/03/2017.
 */
enum class SnackBarType private constructor(val value: Int) {
    SUCCESS(0),
    ERROR(1),
    INFO(2),
    WARNING(3);


    companion object {

        private val map = HashMap<Int, SnackBarType>()

        init {
            for (snackbarType in SnackBarType.values()) {
                map[snackbarType.value] = snackbarType
            }
        }

        fun fromValue(code: Int): SnackBarType? {
            return map[code]
        }
    }
}
