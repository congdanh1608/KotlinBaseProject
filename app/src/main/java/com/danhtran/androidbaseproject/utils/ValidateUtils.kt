package com.danhtran.androidbaseproject.utils

import android.telephony.PhoneNumberUtils
import android.text.TextUtils

import java.util.regex.Pattern

/**
 * Created by DanhTran on 5/31/2019.
 */
object ValidateUtils {
    /**
     * validation for email
     *
     * @param email email
     * @return boolean
     */
    fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * validation for password
     * ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$
     *
     *
     * ^                 # start-of-string
     * (?=.*[0-9])       # a digit must occur at least once
     * (?=.*[a-z])       # a lower case letter must occur at least once
     * (?=.*[A-Z])       # an upper case letter must occur at least once
     * (?=.*[@#$%^&+=])  # a special character must occur at least once you can replace with your special characters
     * (?=\\S+$)          # no whitespace allowed in the entire string
     * .{4,}             # anything, at least six places though
     * $
     *
     *
     *
     * @param password password
     * @return boolean
     */

    fun validatePassword(password: String): Boolean {
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z]).{6,}$"

        val pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }

    /**
     * Accept:
     *
     *
     * 07 5555 6789
     * +61 7 5555 6789
     * 0011+64 7 5555 6789
     * 0418723456
     * +6418723456
     * +61 418 723 456
     *
     * @param phoneNumber phone number
     * @return boolean
     */
    fun validatePhoneNumber(phoneNumber: String): Boolean {
        return if (TextUtils.isEmpty(phoneNumber)) {
            false
        } else PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)

    }
}
