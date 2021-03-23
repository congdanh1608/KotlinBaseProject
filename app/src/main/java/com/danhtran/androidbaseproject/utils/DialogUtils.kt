package com.danhtran.androidbaseproject.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.danhtran.androidbaseproject.R
import com.orhanobut.logger.Logger

/**
 * Created by DanhTran on 6/12/2019.
 */
object DialogUtils {
    /**
     * Show confirm dialog
     */
    fun showConfirmDialog(
        context: Context?,
        icon: Drawable,
        title: String?,
        msg: String,
        cancelBtn: String,
        OkBtn: String,
        isDismiss: Boolean = true,
        callback: (Boolean) -> Unit
    ) {
        if (context == null) {
            return
        }

        val dialog = Dialog(context)

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_notify_two_btn, null)
        dialog.setContentView(view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        val imvIcon: ImageView = view.findViewById(R.id.imv)
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvMsg: TextView = view.findViewById(R.id.tv_msg)
        val btnCancel: TextView = view.findViewById(R.id.btnCancel)
        val btnOk: TextView = view.findViewById(R.id.btnOk)

        imvIcon.setImageDrawable(icon)
        btnCancel.text = cancelBtn
        btnOk.text = OkBtn
        title?.let {
            tvTitle.visibility = View.VISIBLE
            tvTitle.text = title
        } ?: run {
            tvTitle.visibility = View.GONE
        }
        tvMsg.text = msg

        btnOk.setOnClickListener {
            callback(true)

            if (isDismiss) {
                dialog.dismiss()
            }
        }
        btnCancel.setOnClickListener {
            callback(false)

            if (isDismiss) {
                dialog.dismiss()
            }
        }
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        try {
            dialog.show()
        } catch (ex: Exception) {

        }
    }

    /**
     * Show confirm dialog
     */
    fun showConfirmDialog(
        context: Context?,
        icon: Drawable,
        title: String?,
        msg: String,
        btn: String,
        isDismiss: Boolean = true,
        callback: (Boolean) -> Unit
    ) {
        if (context == null) {
            return
        }

        val dialog = Dialog(context)

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_notify_single_btn, null)
        dialog.setContentView(view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        val imvIcon: ImageView = view.findViewById(R.id.imv)
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvMsg: TextView = view.findViewById(R.id.tv_msg)
        val btnOk: TextView = view.findViewById(R.id.btnOk)

        imvIcon.setImageDrawable(icon)
        btnOk.text = btn
        title?.let {
            tvTitle.visibility = View.VISIBLE
            tvTitle.text = title
        } ?: run {
            tvTitle.visibility = View.GONE
        }
        tvMsg.text = msg

        btnOk.setOnClickListener {
            callback(true)

            if (isDismiss) {
                dialog.dismiss()
            }
        }
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        try {
            dialog.show()
        } catch (ex: Exception) {

        }
    }

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