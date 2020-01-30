package com.danhtran.androidbaseproject.utils

/**
 * Created by danhtran on 29/11/2016.
 */

//Default return first ENUM -> Default("")  --> to void crash for some function
object EnumUtils {
    fun <T : Enum<T>> valueOf(enumType: Class<T>, value: String, caseSensitive: Boolean): T? {
        val enumConstants = enumType.enumConstants ?: return null
        for (enumConstant in enumConstants) {
            if (caseSensitive) {
                if (enumConstant.toString() == value) {
                    return enumConstant
                }
            } else {
                if (enumConstant.toString().equals(value, ignoreCase = true)) {
                    return enumConstant
                }
            }
        }
        return enumConstants[0]
    }

    /**
     * Get enum from string value
     *
     * @param enumType enum
     * @param value    value
     * @param <T>      type of enum
     * @return enum
    </T> */
    fun <T : Enum<T>> valueOf(enumType: Class<T>, value: String): T? {
        val enumConstants = enumType.enumConstants ?: return null
        for (enumConstant in enumConstants) {
            if (enumConstant.toString() == value) {
                return enumConstant
            }
        }
        return enumConstants[0]
    }

    /**
     * Get enum from value
     *
     * @param value value
     * @param map   map of enum
     * @param <T>   Type of enum
     * @return enum
    </T> */
    fun <T : Enum<T>> valueOf(value: Int, map: Map<Int, T>): T? {
        return if (map[value] != null) {
            map[value]
        } else map[0]
    }
}
