package com.glide.androidbaseproject.utils

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AlertDialog
import com.glide.androidbaseproject.R

/**
 * Created by DanhTran on 6/12/2019.
 */
object DialogUtils {
    fun showDialog(
        context: Context,
        title: Int,
        message: Int,
        positiveText: Int,
        negativeText: Int,
        listener: DialogListener
    ): AlertDialog.Builder {
        val alertDialog = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom))
        alertDialog.setCancelable(false)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(positiveText) { dialog, which ->
            dialog.cancel()
            listener.positiveClick()
        }
        alertDialog.setNegativeButton(negativeText) { dialog, which ->
            dialog.cancel()
            listener.negativeClick()
        }
        alertDialog.show()
        return alertDialog
    }

    fun showDialog(
        context: Context,
        title: String,
        message: String,
        positiveText: Int,
        negativeText: Int,
        listener: DialogListener
    ): AlertDialog.Builder {
        val alertDialog = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom))
        alertDialog.setCancelable(false)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(positiveText) { dialog, which ->
            dialog.cancel()
            listener.positiveClick()
        }
        alertDialog.setNegativeButton(negativeText) { dialog, which ->
            dialog.cancel()
            listener.negativeClick()
        }
        alertDialog.show()
        return alertDialog
    }

    fun showDialog(context: Context, title: String, message: String, positiveText: String): AlertDialog.Builder {
        val alertDialog = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom))
        alertDialog.setCancelable(false)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(positiveText) { dialog, which -> dialog.cancel() }
        alertDialog.show()
        return alertDialog
    }

    interface DialogListener {
        fun positiveClick()

        fun negativeClick()
    }
}
