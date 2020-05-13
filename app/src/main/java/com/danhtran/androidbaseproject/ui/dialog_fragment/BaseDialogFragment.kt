package com.danhtran.androidbaseproject.ui.dialog_fragment

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
import androidx.lifecycle.Observer
import com.danhtran.androidbaseproject.R
import com.danhtran.androidbaseproject.serviceAPI.extras.ErrorHandler
import com.danhtran.androidbaseproject.ui.activity.BaseAppCompatActivity
import com.danhtran.androidbaseproject.ui.fragment.BaseFragment

/**
 * Created by danhtran on 5/29/15.
 */
abstract class BaseDialogFragment : DialogFragment() {
    /**
     * get View Data Binding
     *
     * @return ViewDataBinding
     */
    protected var binding: ViewDataBinding? = null
        protected set

    /**
     * get view model
     */
    var viewModel: BaseDialogFragmentViewModel? = null
        protected set

    /**
     * Get root view
     *
     * @return root view
     */
    val rootView: View?
        get() = binding?.root

    /**
     * progress layout
     */
    private var progressLayout: View? = null

    protected var isRunning = false    //flag to know is dialog fragment running.
    protected var isDismiss = false    //flag to dismiss the dialog after resume

    /**
     * Get base activity if it is exits.
     *
     * @return BaseAppCompatActivity
     */
    val baseActivity: BaseAppCompatActivity?
        get() = if (activity is BaseAppCompatActivity) {
            activity as BaseAppCompatActivity?
        } else null

    /**
     * Get base fragment if it is exits.
     *
     * @return BaseFragment
     */
    val baseFragment: BaseFragment?
        get() = if (targetFragment is BaseFragment) {
            targetFragment as BaseFragment?
        } else null

    val baseDialogFragment: BaseDialogFragment
        get() = this

    /**
     * set layout for this activity
     *
     * @return init
     */
    abstract fun setLayout(): Int

    /**
     * set progress layout for dialog
     */
    abstract fun setProgressLayout(): View

    /**
     * set type for dialog
     */
    abstract fun setTypeScreen(): TYPE

    /**
     * Set handler + execute view binding
     */
    abstract fun initUI()

    /**
     * Init view model
     */
    abstract fun initViewModel(): BaseDialogFragmentViewModel?

    /**
     * Binding and initialize data into layout
     */
    abstract fun initData()

    /**
     * initialize the listener event
     */
    abstract fun initListener()

    /**
     * change local config
     */
    abstract fun onConfigurationChanged()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireActivity(), R.style.DialogFullScreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = dialog.window
        window?.let {
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            when (setTypeScreen()) {
                TYPE.MATCH_PARENT -> {
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
                TYPE.WRAP_CONTENT -> window.setLayout(
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
        arguments?.let {
            loadPassedParamsIfNeeded(it)
        }

        val xml = setLayout()
        if (xml != 0) {
            binding = DataBindingUtil.inflate(inflater, xml, container, false)
            initUI()

            //listen live event
            viewModel = initViewModel()
            viewModel?.progressState?.observe(this, Observer {
                if (it) {
                    showProgress()
                } else {
                    hideProgress()
                }
            })
            viewModel?.errorHandler?.observe(this, Observer {
                it?.let {
                    ErrorHandler.showError(it, context)
                }
            })

            progressLayout = setProgressLayout()
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
    protected open fun loadPassedParamsIfNeeded(extras: Bundle) {

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

    enum class TYPE constructor(val value: Int) {
        MATCH_PARENT(0),
        WRAP_CONTENT(1)
    }
}
