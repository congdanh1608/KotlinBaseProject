package com.danhtran.androidbaseproject.extras.enums


/**
 * Created by danhtran on 09/04/2017.
 */

class Hashtag {
    //    soibat://search?key=danh

    enum class Host private constructor(val value: String) {
        SEARCH("search"),
        DISCOVER("discover");


        companion object {

            fun fromValue(value: String): Host? {
                for (host in values()) {
                    if (host.value.equals(value, ignoreCase = true)) {
                        return host
                    }
                }
                return null
            }
        }
    }


    enum class Scheme private constructor(val value: String) {
        SOIBAT("soibat");


        companion object {

            fun fromValue(value: String): Scheme? {
                for (scheme in values()) {
                    if (scheme.value.equals(value, ignoreCase = true)) {
                        return scheme
                    }
                }
                return null
            }
        }
    }

    enum class Keyword private constructor(val value: String) {
        KEY("key"),
        GENRES("genres");


        companion object {

            fun fromValue(value: String): Keyword? {
                for (keyword in values()) {
                    if (keyword.value.equals(value, ignoreCase = true)) {
                        return keyword
                    }
                }
                return null
            }
        }
    }
}
