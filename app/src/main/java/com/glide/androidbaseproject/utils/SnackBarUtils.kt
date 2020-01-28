package com.glide.androidbaseproject.utils

import android.app.Activity
import android.content.Context
import android.view.View
import com.glide.androidbaseproject.R
import com.glide.androidbaseproject.extras.enums.SnackBarType
import de.mateware.snacky.Snacky


/**
 * Created by danhtran on 04/08/2017.
 */

object SnackBarUtils {
    /**
     * Show Snack Bar
     *
     * @param view         view
     * @param text         int message
     * @param textColor    int message color
     * @param isCenterText is center message text
     * @param snackBarType type of snack bar
     */
    fun showSnackBar(
        view: View,
        text: Int,
        textColor: Int,
        isCenterText: Boolean,
        snackBarType: SnackBarType
    ) {
        val context = view.context
        val builder = Snacky.builder()
        builder.setView(view)
            .setText(context.getString(text))
            .setTextColor(DeprecatedUtils.getResourceColor(textColor))
            .setDuration(Snacky.LENGTH_SHORT)
        if (isCenterText) builder.centerText()
        setTypeTemplate(builder, snackBarType)
        builder.build().show()
    }

    /**
     * Show Snack Bar
     *
     * @param activity     activity
     * @param text         int message
     * @param textColor    int message color
     * @param isCenterText is center message text
     * @param snackBarType type of snack bar
     */
    fun showSnackBar(
        activity: Activity,
        text: Int,
        textColor: Int,
        isCenterText: Boolean,
        snackBarType: SnackBarType
    ) {
        val context = activity.baseContext
        val builder = Snacky.builder()
        builder.setActivity(activity)
            .setText(context.getString(text))
            .setTextColor(DeprecatedUtils.getResourceColor(textColor))
            .setDuration(Snacky.LENGTH_SHORT)
        if (isCenterText) builder.centerText()
        setTypeTemplate(builder, snackBarType)
        builder.build().show()
    }

    /**
     * Show Snack Bar
     *
     * @param activity     Activity
     * @param text         String message
     * @param textColor    int message color
     * @param isCenterText is center message text
     * @param snackBarType type of snack bar
     */
    fun showSnackBar(
        activity: Activity,
        text: String,
        textColor: Int,
        isCenterText: Boolean,
        snackBarType: SnackBarType
    ) {
        val builder = Snacky.builder()
        builder.setActivity(activity)
            .setText(text)
            .setTextColor(DeprecatedUtils.getResourceColor(textColor))
            .setDuration(Snacky.LENGTH_SHORT)
        if (isCenterText) builder.centerText()
        setTypeTemplate(builder, snackBarType)
        builder.build().show()
    }

    /**
     * Show Snack bar with action
     *
     * @param view            view
     * @param text            int message
     * @param textColor       int message color
     * @param actionText      int action message
     * @param actionColor     int action color
     * @param onClickListener listener for action click event
     * @param snackBarType    snack type
     */
    fun showSnackBarWithAction(
        view: View, text: Int, textColor: Int, actionText: Int, actionColor: Int,
        onClickListener: View.OnClickListener, snackBarType: SnackBarType
    ) {
        val context = view.context
        val builder = Snacky.builder()
        builder.setView(view)
            .setText(context.getString(text))
            .setTextColor(DeprecatedUtils.getResourceColor(textColor))
            .setActionText(actionText)
            .setActionTextColor(actionColor)
            .setActionClickListener(onClickListener)
            .setDuration(Snacky.LENGTH_SHORT)
        setTypeTemplate(builder, snackBarType)
        builder.build().show()
    }

    /**
     * Show Snack bar with action
     *
     * @param activity        activity
     * @param text            int message
     * @param textColor       int message color
     * @param actionText      int action message
     * @param actionColor     int action color
     * @param onClickListener listener for action click event
     * @param snackBarType    snack type
     */
    fun showSnackBarWithAction(
        activity: Activity, text: Int, textColor: Int, actionText: Int, actionColor: Int,
        onClickListener: View.OnClickListener, snackBarType: SnackBarType
    ) {
        val context = activity.baseContext
        val builder = Snacky.builder()
        builder.setActivity(activity)
            .setText(context.getString(text))
            .setTextColor(DeprecatedUtils.getResourceColor(textColor))
            .setActionText(context.getString(actionText))
            .setActionTextColor(actionColor)
            .setActionClickListener(onClickListener)
            .setDuration(Snacky.LENGTH_SHORT)
        setTypeTemplate(builder, snackBarType)
        builder.build().show()
    }

    /**
     * Show indenfinite snack bar with action
     *
     * @param activity        activity
     * @param text            int message
     * @param textColor       int message color
     * @param actionText      int action message
     * @param actionColor     int action color
     * @param onClickListener listener for action click event
     * @param snackBarType    snack bar type
     */
    fun showIndenfiniteSnackBarWithAction(
        activity: Activity, text: Int, textColor: Int, actionText: Int, actionColor: Int,
        onClickListener: View.OnClickListener, snackBarType: SnackBarType
    ) {
        val context = activity.baseContext
        val builder = Snacky.builder()
        builder.setActivity(activity)
            .setText(context.getString(text))
            .setTextColor(DeprecatedUtils.getResourceColor(textColor))
            .setActionText(context.getString(actionText))
            .setActionTextColor(actionColor)
            .setActionClickListener(onClickListener)
            .setDuration(Snacky.LENGTH_INDEFINITE)
        setTypeTemplate(builder, snackBarType)
        builder.build().show()
    }

    /**
     * Show general error message
     *
     * @param context context
     * @param message message text
     */
    fun showGeneralError(context: Context, message: String) {
        showSnackBar(context as Activity, message, R.color.colorWhite, true, SnackBarType.ERROR)
    }

    /**
     * Show general notify message
     *
     * @param context context
     * @param message message text
     */
    fun showGeneralNotify(context: Context, message: String) {
        showSnackBar(context as Activity, message, R.color.colorWhite, true, SnackBarType.INFO)
    }

    //set template for snackbar
    private fun setTypeTemplate(builder: Snacky.Builder, snackBarType: SnackBarType) {
        when (snackBarType) {
            SnackBarType.ERROR -> builder.error()
            SnackBarType.INFO -> builder.info()
            SnackBarType.NORMAL -> {
            }
            SnackBarType.SUCCESS -> builder.success()
            SnackBarType.WARNING -> builder.warning()
        }
    }
}
