package com.danhtran.androidbaseproject.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.danhtran.androidbaseproject.ui.activity.BaseAppCompatActivity

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
    fun showDialogFragment(dialogFragment: DialogFragment, parent: Fragment, tag: String, requestCode: Int) {
        val fragmentManager: FragmentManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            fragmentManager = parent.fragmentManager
        else
            fragmentManager = parent.activity!!.supportFragmentManager
        dialogFragment.setTargetFragment(parent, requestCode)
        dialogFragment.show(fragmentManager!!, tag)
    }

    /**
     * Hide soft keyboard by context and view
     *
     * @param context context
     * @param view    view
     */
    fun hideSoftKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * Hide soft keyboard by activity
     *
     * @param activity activity
     */
    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager != null && activity.currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    }


    /**
     * Show soft keyboard
     *
     * @param context context
     */
    fun showSoftKeyboard(context: Context) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    /**
     * Show soft keyboad with edit text
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
     * Clear focus for edit text
     *
     * @param editText edit text
     * @param activity AppCompatActivity
     */
    fun clearFocus(editText: EditText, activity: BaseAppCompatActivity) {
        editText.clearFocus()
        clearFocus(activity, activity.binding!!.root)
    }

    @JvmOverloads
    fun clearFocus(activity: Activity, layout: View, hideKeyboard: Boolean = true) {
        layout.isFocusable = true
        layout.isFocusableInTouchMode = true
        layout.requestFocus()
        if (hideKeyboard) {
            hideSoftKeyboard(activity, layout)
        }
    }

    /**
     * Add keyboard event to hide soft keyboard
     *
     * @param activity   activity
     * @param rootLayout root layout
     * @param view       view
     */
    fun addKeyboardEvents(activity: Activity, rootLayout: View, view: View) {

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
    private fun isAnyOtherEditTextHasFocus(view: View, editText: EditText): Boolean {

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
    fun removeKeyboardEvents(view: View) {
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
}
