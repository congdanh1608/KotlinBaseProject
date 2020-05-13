package com.danhtran.androidbaseproject.extras.enums

import com.danhtran.androidbaseproject.MyApplication

/**
 * Created by danhtran on 09/04/2017.
 */

enum class HawkKey private constructor(internal val value: String) {
    IS_NOT_FIRST_VIEW("is_not_first_view"),

    SESSION_LOGIN("SESSION_LOGIN"),
    TOKEN_FIREBASE("TOKEN_FIREBASE"),
    PUSH_ID_FIREBASE("PUSH_ID_FIREBASE"),
    OFF_NOTIFY("OFF_NOTIFY"),
    LANGUAGE("LANGUAGE");

    fun getValue(): String {
        return PREFIX + this.value
    }

    companion object {

        val PREFIX = MyApplication.instance().packageName

        fun fromValue(value: String): HawkKey? {
            for (hawkKey in values()) {
                val keyValue = PREFIX + hawkKey.value
                if (value.equals(keyValue, ignoreCase = true)) {
                    return hawkKey
                }
            }
            return null
        }
    }
}
