package com.danhtran.androidbaseproject.ui.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.danhtran.androidbaseproject.R
import com.danhtran.androidbaseproject.extras.MyContextWrapper
import com.danhtran.androidbaseproject.extras.enums.HawkKey
import com.danhtran.androidbaseproject.services.extras.ErrorHandler
import com.danhtran.androidbaseproject.ui.activity.full.FullScreenActivity
import com.danhtran.androidbaseproject.ui.activity.main.MainActivity
import com.danhtran.androidbaseproject.ui.activity.secondary.SecondaryActivity
import com.danhtran.androidbaseproject.ui.activity.splash.SplashActivity
import com.danhtran.androidbaseproject.ui.activity.tour.TourActivity
import com.danhtran.androidbaseproject.ui.fragment.BaseFragment
import com.danhtran.androidbaseproject.utils.UIUtils
import com.livefront.bridge.Bridge
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.Logger
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import java.util.*

/**
 * Created by danhtran on 2/26/2018.
 */

abstract class BaseAppCompatActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    /**
     * get View Data Binding
     *
     * @return ViewDataBinding
     */
    var binding: ViewDataBinding? = null
        protected set

    /**
     * get view model
     */
    var viewModel: BaseActivityViewModel? = null
        protected set

    /**
     * Get Fragment Manager
     *
     * @return FragmentManager
     */
    var myFragmentManager: FragmentManager? = null
        protected set
    private var backButtonCount = 0

    protected var setOfDialogs: MutableCollection<Dialog> = LinkedHashSet()

    /**
     * get root view of this activity
     *
     * @return view
     */
    val rootView: View?
        get() = binding?.root

    val baseActivity: BaseAppCompatActivity
        get() = this

    /**
     * Get name of previous fragment
     *
     * @return name
     */
    val previousFragmentName: String?
        get() {
            val count = myFragmentManager?.backStackEntryCount ?: 0
            return if (count > 1) {
                myFragmentManager?.getBackStackEntryAt(count - 2)?.name
            } else null
        }

    /**
     * Get name of current fragment
     *
     * @return name
     */
    val currentFragmentName: String?
        get() {
            val count = myFragmentManager?.backStackEntryCount ?: 0
            return if (count > 0) {
                myFragmentManager?.getBackStackEntryAt(count - 1)?.name
            } else null
        }

    /**
     * Get current fragment
     *
     * @return fragment
     */
    val currentFragment: Fragment?
        get() {
            myFragmentManager?.let {
                val fragmentName = currentFragmentName
                if (!fragmentName.isNullOrEmpty())
                    return myFragmentManager?.findFragmentByTag(fragmentName)
            }
            return null
        }

    /**
     * Get children manager
     *
     * @return FragmentManager
     */
    val childManager: FragmentManager?
        get() {
            val currentFragment = currentFragment
            return currentFragment?.childFragmentManager
        }

    /**
     * Get name of current children fragment
     *
     * @return name
     */
    val currentChildFragmentName: String?
        get() {
            val childManager = childManager
            childManager?.let {
                val count = childManager.backStackEntryCount
                if (count > 0) {
                    return childManager.getBackStackEntryAt(count - 1).name
                }
            }
            return null
        }

    fun addDialog(dialog: Dialog?) {
        dialog?.let {
            setOfDialogs.add(dialog)
        }
    }

    /**
     * set layout for this activity
     *
     * @return init
     */
    abstract fun setLayout(): Int

    /**
     * Set handler + execute view binding
     */
    abstract fun initUI()

    /**
     * Init view model
     */
    abstract fun initViewModel(): BaseActivityViewModel?

    /**
     * Binding and initialize data into layout
     */
    abstract fun initData()

    /**
     * initialize the listener event
     */
    abstract fun initListener()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Bridge.restoreInstanceState(this, savedInstanceState)

        registerReceiver()

        //set portrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //check and load intent params
        intent?.extras?.let {
            loadPassedParamsIfNeeded(it)
        }

        //binding layout
        val xml = setLayout()
        if (xml != 0 && binding == null) {
            binding = DataBindingUtil.setContentView(this, xml)
            //hide keyboard
            UIUtils.addKeyboardEvents(this, binding?.root, binding?.root)
        }

        //init
        initFragmentManager()
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
                ErrorHandler.showError(it, this)
            }
        })

        //init
        initData()
        initListener()
    }

    override fun attachBaseContext(newBase: Context) {
        //apply language
        val lang: Int = Hawk.get(HawkKey.LANGUAGE.value, 0)
        val language = Locale(
            if (lang == 1) {
                Locale.ITALIAN.language
            } else {
                Locale.ENGLISH.language
            }
        )
        //attach base context for calligraphy font
        super.attachBaseContext(ViewPumpContextWrapper.wrap(MyContextWrapper.wrap(newBase, language.language)))
    }

    override fun onStart() {
        super.onStart()
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onDestroy() {
        Bridge.clear(this)

        binding?.let {
            UIUtils.removeKeyboardEvents(it.root)
        }

        unRegisterReceiver()

        destroyProgressDialog()

        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.let { Bridge.saveInstanceState(this, it) }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransitionExit()
    }

    override fun onBackStackChanged() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    /**
     * load passed params
     */
    protected open fun loadPassedParamsIfNeeded(extras: Bundle) {

    }

    /**
     * Register receiver in here
     */
    private fun registerReceiver() {

    }

    /**
     * Unregister receiver in here
     */
    private fun unRegisterReceiver() {

    }

    //init fragment manager
    private fun initFragmentManager() {
        myFragmentManager = supportFragmentManager
        myFragmentManager?.addOnBackStackChangedListener(this)
    }

    //call on root activity
    fun exitApp() {
        //check exit app
        if (backButtonCount >= 1) {
            finish()
        } else {
            Toast.makeText(this, R.string.message_close_app, Toast.LENGTH_SHORT).show()
            backButtonCount++
            Handler().postDelayed({ backButtonCount = 0 }, (3 * 1000).toLong())
        }
    }

    /**
     * set fragment for current activity
     *
     * @param tag            tag name
     * @param data           data
     * @param isAddBackStack does add this fragment into backstack?
     */
    @JvmOverloads
    fun setFragment(tag: String, bundle: Bundle?, isAddBackStack: Boolean = true) {
        val fragmentPopped = myFragmentManager?.popBackStackImmediate(tag, 0) ?: false
        if (!fragmentPopped) {
            val fragment = getFragment(tag)
            bundle?.let {
                fragment?.arguments = it
            }
            if (fragment != null) {
                val transaction = myFragmentManager?.beginTransaction()
                transaction?.setCustomAnimations(
                    R.anim.slide_from_right,
                    R.anim.slide_to_left,
                    R.anim.slide_from_left,
                    R.anim.slide_to_right
                )
                transaction?.replace(R.id.content_fragment, fragment, tag)
                if (isAddBackStack)
                    transaction?.addToBackStack(tag)
                transaction?.commit()
            } else
                Logger.e("Forgot add fragment into base activity!")
        }
    }

    //get fragment by tag and data
    private fun getFragment(tag: String): BaseFragment? {
        when (tag) {

        }
        return null
    }

    /**
     * Start activity
     *
     * @param tag      tag name of activity
     * @param bundle   bundle
     * @param isFinish is finish previous activity?
     */
    @JvmOverloads
    fun startActivity(tag: String, bundle: Bundle?, isFinish: Boolean = false) {
        val intent = getIntentActivity(tag)

        intent?.let {
            bundle?.let {
                intent.putExtras(bundle)
            }
            startActivity(intent)
            if (isFinish) {
                super.finish()     //don't user animation on here. because startActivity had already
            }
        }
    }

    /**
     * start activity and clear all others
     *
     * @param tag    tag name
     * @param bundle bundle
     */
    fun startActivityAsRoot(tag: String, bundle: Bundle?) {
        val intent = getIntentActivity(tag)

        intent?.let {
            bundle?.let {
                intent.putExtras(bundle)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            super.finish()
        }
    }

    fun startActivityForResult(tag: String, bundle: Bundle, requestCode: Int) {
        startActivityForResult(tag, bundle, true, requestCode)
    }

    fun startActivityForResult(tag: String, bundle: Bundle?, isFinish: Boolean, requestCode: Int) {
        val intent = getIntentActivity(tag)

        intent?.let {
            bundle?.let {
                intent.putExtras(bundle)
            }
            startActivityForResult(intent, requestCode)
            if (isFinish) {
                super.finish()
            }
        }
    }

    /**
     * get intent of activity by tag
     *
     * @param tag tag is tag of activity contain the intent
     * @return intent
     */
    private fun getIntentActivity(tag: String): Intent? {
        var intent: Intent? = null
        if (tag.isNotEmpty()) {
            when (tag) {
                MainActivity::class.java.name -> {
                    intent = MainActivity.createIntent(this)
                }
                TourActivity::class.java.name -> {
                    intent = TourActivity.createIntent(this)
                }
                FullScreenActivity::class.java.name -> {
                    intent = FullScreenActivity.createIntent(this)
                }
                SecondaryActivity::class.java.name -> {
                    intent = SecondaryActivity.createIntent(this)
                }
                SplashActivity::class.java.name -> {
                    intent = SplashActivity.createIntent(this)
                }
            }
        }
        return intent
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransitionEnter()
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
        overridePendingTransitionEnter()
    }

    override fun finish() {
        super.finish()
        overridePendingTransitionExit()
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    //region Show Background Progress
    private fun createProgressDialog() {
        val progressDialog = Dialog(this, R.style.DialogFullScreen)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.process_dialog, null)
        //show view
        val view = dialogView.findViewById<View>(R.id.progressBar)
        view.visibility = View.VISIBLE

        progressDialog.setContentView(dialogView)
        progressDialog.setCancelable(false)

        val dialogWindow = progressDialog.window
        dialogWindow?.let {
            dialogWindow.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialogWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        progressDialog.show()

        addDialog(progressDialog)
    }

    private fun destroyProgressDialog() {
        for (dialog in setOfDialogs) {
            dialog.dismiss()
        }
    }

    /**
     * Show progress layout
     */
    fun showProgress() {
        //init progress dialog
        createProgressDialog()
    }

    /**
     * Hide progress layout
     */
    fun hideProgress() {
        destroyProgressDialog()
    }
}
