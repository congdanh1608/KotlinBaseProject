package com.glide.androidbaseproject.ui.dialog_fragment

import android.app.Dialog
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.glide.androidbaseproject.R
import com.glide.androidbaseproject.ui.activity.BaseAppCompatActivity
import com.glide.androidbaseproject.ui.fragment.BaseFragment

/**
 * Created by danhtran on 5/29/15.
 */
abstract class BaseDialogFragment : DialogFragment() {
    protected lateinit var rootView: View
    protected var binding: ViewDataBinding? = null
    private var progressLayout: View? = null

    protected var isRunning = false    //flag to know is dialog fragment running.
    protected var isDismiss = false    //flag to dismiss the dialog after resume

    val baseActivity: BaseAppCompatActivity?
        get() = if (activity is BaseAppCompatActivity) {
            activity as BaseAppCompatActivity?
        } else null

    val baseFragment: BaseFragment?
        get() = if (targetFragment is BaseFragment) {
            targetFragment as BaseFragment?
        } else null

    val baseDialogFragment: BaseDialogFragment
        get() = this

    abstract fun setLayout(): Int

    abstract fun setProgressLayout(): View

    abstract fun setTypeScreen(): TYPE

    abstract fun initUI()

    abstract fun initData()

    abstract fun initListener()

    abstract fun onConfigurationChanged()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(activity!!, R.style.DialogFullScreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = dialog.window
        window?.let {
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            when (setTypeScreen()) {
                BaseDialogFragment.TYPE.MATCH_PARENT -> {
                    val root = RelativeLayout(activity)
                    root.layoutParams =
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    window.setContentView(root)
                    window.setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
                BaseDialogFragment.TYPE.WRAP_CONTENT -> window.setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }
        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val xml = setLayout()
        if (xml != 0) {
            binding = DataBindingUtil.inflate(inflater, xml, container, false)
            initUI()
            rootView = binding!!.root
            progressLayout = setProgressLayout()

            arguments?.let {
                loadPassedParamsIfNeeded(arguments!!)
            }
        }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initListener()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        onConfigurationChanged()
    }

    /**
     * load passed params
     */
    protected fun loadPassedParamsIfNeeded(extras: Bundle) {

    }

    override fun onPause() {
        super.onPause()
        isRunning = false
    }

    override fun onResume() {
        super.onResume()
        isRunning = true

        if (isDismiss) {
            dismiss()
        }
    }

    override fun dismiss() {
        if (isRunning) {
            super.dismiss()
        } else {
            isDismiss = true       //will dismiss when fragment is visible
        }
    }

    fun showProgress() {
        progressLayout?.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressLayout?.visibility = View.GONE
    }

    enum class TYPE private constructor(val value: Int) {
        MATCH_PARENT(0),
        WRAP_CONTENT(1)
    }
}
