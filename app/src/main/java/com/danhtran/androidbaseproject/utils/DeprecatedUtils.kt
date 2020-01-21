package com.danhtran.androidbaseproject.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.view.ViewTreeObserver
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.danhtran.androidbaseproject.MyApplication

import java.util.Locale


/**
 * Created by danhtran on 15/10/2016.
 */
object DeprecatedUtils {
    private val SDK_VERSION = Build.VERSION.SDK_INT

    fun getResourceColor(resource: Int): Int {
        val context = MyApplication.instance().applicationContext
        return if (SDK_VERSION >= Build.VERSION_CODES.LOLLIPOP) {
            ContextCompat.getColor(context, resource)
        } else {
            context.resources.getColor(resource)
        }
    }

    fun getResourceDrawable(resource: Int): Drawable {
        val context = MyApplication.instance().applicationContext
        return if (SDK_VERSION >= Build.VERSION_CODES.LOLLIPOP) {
            context.resources.getDrawable(resource, context.theme)
        } else {
            context.resources.getDrawable(resource)
        }
    }

    fun setLocale(configuration: Configuration, myLocale: Locale) {
        if (SDK_VERSION >= Build.VERSION_CODES.N) {
            configuration.setLocale(myLocale)
        } else {
            configuration.locale = myLocale
        }
    }

    fun getLocale(resources: Resources): Locale {
        return if (SDK_VERSION >= Build.VERSION_CODES.N) {
            resources.configuration.locales.get(0)
        } else {

            resources.configuration.locale
        }
    }

    fun removeOnGlobalLayoutListener(
        obs: ViewTreeObserver,
        onGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener
    ) {
        if (SDK_VERSION >= Build.VERSION_CODES.JELLY_BEAN) {
            obs.removeOnGlobalLayoutListener(onGlobalLayoutListener)
        } else {
            obs.removeGlobalOnLayoutListener(onGlobalLayoutListener)
        }
    }

    fun fromHtml(text: String): Spanned? {
        var result: Spanned? = null
        if (!TextUtils.isEmpty(text)) {
            if (SDK_VERSION >= Build.VERSION_CODES.N) {
                result = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
            } else {
                result = Html.fromHtml(text)
            }
        }
        return result
    }

    fun toHtml(text: Spanned): String {
        var result = ""
        if (!TextUtils.isEmpty(text)) {
            if (SDK_VERSION >= Build.VERSION_CODES.N) {
                result = Html.toHtml(text, Html.FROM_HTML_MODE_LEGACY)
            } else {
                result = Html.toHtml(text)
            }
        }
        return result
    }

    //get resource vector or png
    fun getImageVector(resourceVector: Int, resourcePNG: Int): Int {
        return if (SDK_VERSION >= Build.VERSION_CODES.LOLLIPOP) {     //use vector
            resourceVector
        } else {                                                         //use png
            resourcePNG
        }
    }

    fun getDrawableVector(resourceVector: Int, resourcePNG: Int): Drawable {
        return getResourceDrawable(getImageVector(resourceVector, resourcePNG))
    }

    fun removeRule(layoutParams: RelativeLayout.LayoutParams, verb: Int) {
        if (SDK_VERSION >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.removeRule(verb)
        } else {
            layoutParams.addRule(verb, 0)
        }
    }
}
