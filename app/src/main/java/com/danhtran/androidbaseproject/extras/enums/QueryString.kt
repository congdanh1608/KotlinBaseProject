package com.danhtran.androidbaseproject.extras.enums

/**
 * Created by danhtran on 09/04/2017.
 */

enum class QueryString private constructor(internal val value: String) {
    ID("id"),

    ;

    fun getValue(): String {
        return this.value
    }

    companion object {

        fun fromValue(value: String): QueryString? {
            for (queryString in values()) {
                if (queryString.value.equals(value, ignoreCase = true)) {
                    return queryString
                }
            }
            return null
        }
    }
}
