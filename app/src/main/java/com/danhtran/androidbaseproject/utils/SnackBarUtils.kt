package com.danhtran.androidbaseproject.utils

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.danhtran.androidbaseproject.R
import com.danhtran.androidbaseproject.extras.enums.SnackBarType
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
     * @param isCenterText is center message text
     * @param snackBarType type of snack bar
     */
    fun showSnackBar(view: View, text: Int, isCenterText: Boolean, snackBarType: SnackBarType) {
        val context = view.context
        val builder = Snacky.builder()
        builder.setView(view)
            .setText(context.getString(text))
            .setDuration(Snacky.LENGTH_SHORT)
        if (isCenterText) builder.centerText()
        setStyle(builder, context, snackBarType)
        builder.build().show()
    }

    /**
     * Show Snack Bar
     *
     * @param view         view
     * @param text         message
     * @param isCenterText is center message text
     * @param snackBarType type of snack bar
     */
    fun showSnackBar(view: View, text: String, isCenterText: Boolean, snackBarType: SnackBarType) {
        val context = view.context
        val builder = Snacky.builder()
        builder.setView(view)
            .setText(text)
            .setDuration(Snacky.LENGTH_SHORT)
        if (isCenterText) builder.centerText()
        setStyle(builder, context, snackBarType)
        builder.build().show()
    }

    /**
     * Show Snack bar with action
     *
     * @param view            view
     * @param text            int message
     * @param actionText      int action message
     * @param onClickListener listener for action click event
     * @param snackBarType    snack type
     */
    fun showSnackBarWithAction(
        view: View, text: Int, actionText: Int,
        onClickListener: View.OnClickListener, snackBarType: SnackBarType
    ) {
        val context = view.context
        val builder = Snacky.builder()
        builder.setView(view)
            .setText(context.getString(text))
            .setActionText(actionText)
            .setActionClickListener(onClickListener)
            .setDuration(Snacky.LENGTH_SHORT)
        setStyle(builder, context, snackBarType)
        builder.build().show()
    }

    /**
     * Show indenfinite snack bar with action
     *
     * @param view            view
     * @param text            int message
     * @param actionText      int action message
     * @param onClickListener listener for action click event
     * @param snackBarType    snack bar type
     */
    fun showIndenfiniteSnackBarWithAction(
        view: View, text: Int, actionText: Int,
        onClickListener: View.OnClickListener, snackBarType: SnackBarType
    ) {
        val context = view.context
        val builder = Snacky.builder()
        builder.setView(view)
            .setText(context.getString(text))
            .setActionText(context.getString(actionText))
            .setActionClickListener(onClickListener)
            .setDuration(Snacky.LENGTH_INDEFINITE)
        setStyle(builder, context, snackBarType)
        builder.build().show()
    }

    /**
     * Show general error message
     *
     * @param parentView parentView
     * @param message    message text
     */
    fun showGeneralError(parentView: View, message: String) {
        showSnackBar(parentView, message, true, SnackBarType.ERROR)
    }

    /**
     * Show general notify message
     *
     * @param parentView parent view
     * @param message    message text
     */
    fun showGeneralNotify(parentView: View, message: String) {
        showSnackBar(parentView, message, true, SnackBarType.INFO)
    }

    //set style for snack bar
    private fun setStyle(builder: Snacky.Builder, context: Context, snackBarType: SnackBarType) {
        when (snackBarType) {
            SnackBarType.ERROR -> {
                builder.setBackgroundColor(ContextCompat.getColor(context, R.color.colorError))
                builder.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
                builder.error()
            }
            SnackBarType.INFO -> {
                builder.setBackgroundColor(ContextCompat.getColor(context, R.color.colorInfo))
                builder.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
                builder.info()
            }
            SnackBarType.SUCCESS -> {
                builder.setBackgroundColor(ContextCompat.getColor(context, R.color.colorSuccess))
                builder.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
                builder.success()
            }
            SnackBarType.WARNING -> {
                builder.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWarning))
                builder.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
                builder.warning()
            }
        }
    }
}
