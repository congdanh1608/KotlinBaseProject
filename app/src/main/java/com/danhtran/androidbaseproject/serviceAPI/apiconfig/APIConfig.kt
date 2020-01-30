package com.danhtran.androidbaseproject.serviceAPI.apiconfig

import com.danhtran.androidbaseproject.BuildConfig

/**
 * Created by danhtran on 1/31/16.
 */
object APIConfig {

    val TERMS_URL = "https:///.../app_terms_and_conditions.pdf"
    val POLICY_URL = "https://.../privacy_policy.pdf"

    val DIRECTION_URL = "https://maps.googleapis.com/maps/api/directions/json"
    lateinit var ANONYMOUS_URL: String

    init {
        when (BuildConfig.FLAVOR) {
            "develop" -> ANONYMOUS_URL = ""
            "staging" -> ANONYMOUS_URL = ""
            "production" -> ANONYMOUS_URL = ""
        }
    }

}
