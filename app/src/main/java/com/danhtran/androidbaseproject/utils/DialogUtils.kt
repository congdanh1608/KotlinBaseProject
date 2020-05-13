package com.danhtran.androidbaseproject.utils

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AlertDialog
import com.danhtran.androidbaseproject.R
import com.orhanobut.logger.Logger

/**
 * Created by DanhTran on 6/12/2019.
 */
object DialogUtils {
    fun showDialog(
        context: Context?,
        title: Int,
        message: Int,
        positiveText: Int,
        negativeText: Int,
        listener: DialogListener
    ): AlertDialog? {
        context?.let {
            val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom))
            builder.setCancelable(false)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(positiveText) { dialog, which ->
                dialog.cancel()
                listener.positiveClick()
            }
            builder.setNegativeButton(negativeText) { dialog, which ->
                dialog.cancel()
                listener.negativeClick()
            }

            return showDialog(builder)
        }
        return null
    }

    fun showDialog(
        context: Context?,
        title: String,
        message: String,
        positiveText: Int,
        negativeText: Int,
        listener: DialogListener
    ): AlertDialog? {
        context?.let {
            val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom))
            builder.setCancelable(false)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(positiveText) { dialog, which ->
                dialog.cancel()
                listener.positiveClick()
            }
            builder.setNegativeButton(negativeText) { dialog, which ->
                dialog.cancel()
                listener.negativeClick()
            }
            return showDialog(builder)
        }
        return null
    }

    fun showDialog(context: Context?, title: String, message: String, positiveText: String): AlertDialog? {
        context?.let {
            val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom))
            builder.setCancelable(false)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(positiveText) { dialog, which -> dialog.cancel() }
            return showDialog(builder)
        }
        return null
    }

    fun showDialog(context: Context?, title: Int, message: Int, positiveText: Int, listener: DialogListener): AlertDialog? {
        context?.let {
            val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom))
            builder.setCancelable(true)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(positiveText) { dialog, which ->
                dialog.cancel()
                listener.positiveClick()
            }
            return showDialog(builder)
        }
        return null
    }

    private fun showDialog(alertDialog: AlertDialog.Builder): AlertDialog? {
        try {
            alertDialog.show()
        } catch (ex: Exception) {
            ex.message?.let {
                Logger.e(ex.message!!)
            }
        }
        return null
    }

    interface DialogListener {
        fun positiveClick()

        fun negativeClick()
    }
}