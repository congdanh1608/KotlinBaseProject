package com.glide.androidbaseproject.extras.enums


import com.glide.androidbaseproject.utils.EnumUtils

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
                return EnumUtils.valueOf(Host::class.java, value)
            }
        }
    }


    enum class Scheme private constructor(val value: String) {
        SOIBAT("soibat");


        companion object {

            fun fromValue(value: String): Scheme? {
                return EnumUtils.valueOf(Scheme::class.java, value)
            }
        }
    }

    enum class Keyword private constructor(val value: String) {
        KEY("key"),
        GENRES("genres");


        companion object {

            fun fromValue(value: String): Keyword? {
                return EnumUtils.valueOf(Keyword::class.java, value)
            }
        }
    }
}
