package com.danhtran.androidbaseproject.extras.enums

/**
 * Created by danhtran on 09/04/2017.
 */

enum class EventBusKey private constructor(val value: String) {
    SIGN_IN_OUT("signInOut"),

    NOTIFICATION_SHOW("notification_show"),
    NOTIFICATION_HIDE("notification_hide"),

    APPLY_FILTER("apply_filter"),

    PERMISSION_CHANGE("permission_change"),
    GPS_ON_OFF("gps_on_off"),

    UPDATE_ACCOUNT_SCREEN("update_account_screen"),

    NOTIFICATION_OFFERS("notification_offers"),
    NOTIFICATION_SOLUTIONS("notification_solutions"),

    NETWORK_IS_AVAILABLE("network_is_available");


    companion object {

        fun fromValue(value: String): EventBusKey? {
            for (eventBusKey in values()) {
                if (eventBusKey.value.equals(value, ignoreCase = true)) {
                    return eventBusKey
                }
            }
            return null
        }
    }
}
