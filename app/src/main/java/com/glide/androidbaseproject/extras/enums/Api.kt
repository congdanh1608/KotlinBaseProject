package com.glide.androidbaseproject.extras.enums

/**
 * Created by danhtran on 09/04/2017.
 */

enum class Api private constructor(val value: String) {
    LOGIN("signin"),
    SIGNUP("signup"),
    AUTHEN("auth"),
    CHARACTER("character"),
    EVENTS("events")
}
