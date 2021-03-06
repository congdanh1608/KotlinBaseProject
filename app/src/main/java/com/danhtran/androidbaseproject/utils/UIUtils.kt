package com.danhtran.androidbaseproject.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.danhtran.androidbaseproject.extras.enums.AppTheme
import com.danhtran.androidbaseproject.extras.enums.HawkKey
import com.danhtran.androidbaseproject.ui.activity.BaseAppCompatActivity
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.Logger

/**
 * Created by danhtran on 04/06/2017.
 */

object UIUtils {
    /**
     * Show Dialog fragment
     *
     * @param dialogFragment dialog fragment
     * @param parent         parent fragment
     * @param tag            tag name of dialog fragment
     * @param requestCode    request code for result returned
     */
    fun showDialogFragment(
        dialogFragment: DialogFragment,
        parent: Fragment,
        tag: String,
        requestCode: Int
    ) {
        val fragmentManager: FragmentManager = parent.parentFragmentManager
        dialogFragment.setTargetFragment(parent, requestCode)
        dialogFragment.show(fragmentManager, tag)
    }


    /**
     * Show Dialog fragment
     *
     * @param dialogFragment dialog fragment
     * @param activity        AppCompatActivity
     * @param tag            tag name of dialog fragment
     */
    fun showDialogFragment(
        dialogFragment: DialogFragment,
        activity: AppCompatActivity,
        tag: String
    ) {
        val ft = activity.supportFragmentManager.beginTransaction()
        val prev = activity.supportFragmentManager.findFragmentByTag(tag)
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        dialogFragment.show(ft, tag)
    }

    /**
     * Hide soft keyboard by context and view
     *
     * @param context context
     * @param view    view
     */
    fun hideSoftKeyboard(context: Context, view: View) {
        context.let {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * Hide soft keyboard by activity
     *
     * @param activity activity
     */
    fun hideSoftKeyboard(activity: Activity) {
        activity.let {
            val inputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            activity.currentFocus?.let {
                inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
            }
        }
    }

    /**
     * Show soft keyboard
     *
     * @param context context
     */
    fun showSoftKeyboard(context: Context) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    /**
     * Show soft keyboard with edit text
     *
     * @param editText edit text
     * @param context  context
     */
    fun showSoftKeyboard(editText: EditText, context: Context) {
        editText.requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * show soft keyboard with edit text after 500ms
     */
    fun showSoftKeyboardDelay(editText: EditText, context: Context) {
        Handler().postDelayed({
            editText.requestFocus()
            val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }, 500)
    }

    fun clearDialogFocus(activity: Activity, dialogLayout: View) {
        dialogLayout.isFocusable = true
        dialogLayout.isFocusableInTouchMode = true
        dialogLayout.requestFocus()
        hideSoftKeyboard(activity, dialogLayout)
    }


    /**
     * Clear focus for edit text
     *
     * @param editText edit text
     * @param activity AppCompatActivity
     */
    fun clearFocus(editText: EditText, activity: BaseAppCompatActivity) {
        editText.clearFocus()
        clearFocus(activity, activity.binding?.root)
    }

    @JvmOverloads
    fun clearFocus(activity: Activity, layout: View?, hideKeyboard: Boolean = true) {
        layout?.let {
            layout.isFocusable = true
            layout.isFocusableInTouchMode = true
            layout.requestFocus()
            if (hideKeyboard) {
                hideSoftKeyboard(activity, layout)
            }
        }
    }

    /**
     * Add keyboard event to hide soft keyboard
     *
     * @param activity   activity
     * @param rootLayout root layout
     * @param view       view
     */
    fun addKeyboardEvents(activity: BaseAppCompatActivity, rootLayout: View?, view: View?) {

        if (view is EditText) {
            view.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus && !isAnyOtherEditTextHasFocus(rootLayout, view)) {
                    // If the edit text is lost focus and there is not any other edit text has focus,
                    // We hide the keyboard
                    hideSoftKeyboard(activity, v)
                }
            }
        }

        if (view is ViewGroup) {
            //If a layout container, iterate over children
            for (idx in 0 until view.childCount) {
                val child = view.getChildAt(idx)
                addKeyboardEvents(activity, rootLayout, child)
            }
        }
    }

    //check is any other edit text has focus?
    private fun isAnyOtherEditTextHasFocus(view: View?, editText: EditText): Boolean {

        var result = false
        if (view !== editText && view is EditText && view.hasFocus()) {

            result = true
        } else if (view is ViewGroup) {

            //If a layout container, iterate over children
            var idx = 0
            while (idx < view.childCount && !result) {
                val child = view.getChildAt(idx)
                result = isAnyOtherEditTextHasFocus(child, editText)
                idx++
            }
        }

        return result
    }

    /**
     * Remove keyboard events to hide keyboard after click outside of edit text
     *
     * @param view view
     */
    fun removeKeyboardEvents(view: View?) {
        if (view is EditText) {
            view.onFocusChangeListener = null
        }

        if (view is ViewGroup) {
            //If a layout container, iterate over children
            for (idx in 0 until view.childCount) {
                val child = view.getChildAt(idx)
                removeKeyboardEvents(child)
            }
        }
    }

    fun setAppTheme() {
        val appThem = Hawk.get<AppTheme>(HawkKey.SETTING_APP_THEME.value, AppTheme.LIGHT)
        appThem?.let {
            when (appThem) {
                AppTheme.DARK -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                AppTheme.LIGHT -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }

    fun restartActivity(activity: BaseAppCompatActivity?) {
        activity?.let {
            val intent = activity.intent
            activity.overridePendingTransition(0, 0)
            intent?.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            activity.finish()
            //restart the activity without animation
            activity.overridePendingTransition(0, 0)
            activity.startActivity(intent)
        }
    }

    fun getColor(attrColor: Int, context: Context?): Int? {
        context?.let {
            val value = TypedValue()
            context.theme.resolveAttribute(attrColor, value, true)
            return value.data
        }
        return null
    }

    fun showToast(context: Context?, content: Int) {
        try {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            ex.message?.let {
                Logger.e(ex.message!!)
            }
        }
    }

    fun showLongToast(context: Context?, content: Int) {
        try {
            Toast.makeText(context, content, Toast.LENGTH_LONG).show()
        } catch (ex: Exception) {
            ex.message?.let {
                Logger.e(ex.message!!)
            }
        }
    }

    fun showToast(context: Context?, content: String) {
        try {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            ex.message?.let {
                Logger.e(ex.message!!)
            }
        }
    }

    fun showLongToast(context: Context?, content: String) {
        try {
            Toast.makeText(context, content, Toast.LENGTH_LONG).show()
        } catch (ex: Exception) {
            ex.message?.let {
                Logger.e(ex.message!!)
            }
        }
    }
}
