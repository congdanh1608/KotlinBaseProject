package com.glide.androidbaseproject.extras.enums

/**
 * Created by danhtran on 09/04/2017.
 */

class Header {

    enum class HeaderValue private constructor(val value: String) {
        AUTHORIZATION("Authorization")
    }

    enum class TypeHeader private constructor(val value: String) {
        BEARER("Bearer ")
    }
}
