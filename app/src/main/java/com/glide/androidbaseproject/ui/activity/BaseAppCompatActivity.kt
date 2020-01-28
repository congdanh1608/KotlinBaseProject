package com.glide.androidbaseproject.ui.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.glide.androidbaseproject.R
import com.glide.androidbaseproject.ui.activity.main.MainActivity
import com.glide.androidbaseproject.ui.activity.tour.TourActivity
import com.glide.androidbaseproject.ui.fragment.BaseFragment
import com.glide.androidbaseproject.utils.UIUtils
import com.orhanobut.logger.Logger
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
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
     * Get Fragment Manager
     *
     * @return FragmentManager
     */
    var myFragmentManager: FragmentManager? = null
        protected set
    private var backButtonCount = 0

    private var progressDialog: Dialog? = null
    protected var setOfDialogs: MutableCollection<Dialog> = LinkedHashSet()

    /**
     * get root view of this activity
     *
     * @return view
     */
    val rootView: View?
        get() = if (binding != null) {
            binding!!.root
        } else null

    val baseActivity: BaseAppCompatActivity
        get() = this

    /**
     * Get name of previous fragment
     *
     * @return name
     */
    val previousFragmentName: String?
        get() {
            val count = myFragmentManager!!.backStackEntryCount
            return if (count > 1) myFragmentManager!!.getBackStackEntryAt(count - 2).name else null
        }

    /**
     * Get name of current fragment
     *
     * @return name
     */
    val currentFragmentName: String?
        get() {
            val count = myFragmentManager!!.backStackEntryCount
            return if (count > 0) myFragmentManager!!.getBackStackEntryAt(count - 1).name else null
        }

    /**
     * Get current fragment
     *
     * @return fragment
     */
    val currentFragment: Fragment?
        get() {
            if (myFragmentManager != null) {
                val fragmentName = currentFragmentName
                if (!TextUtils.isEmpty(fragmentName))
                    return myFragmentManager!!.findFragmentByTag(fragmentName)
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
            if (childManager != null) {
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
     * Binding and initialize data into layout
     */
    abstract fun initData()

    /**
     * initialize the listener event
     */
    abstract fun initListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver()

        //set portrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        //binding layout
        val xml = setLayout()
        if (xml != 0 && binding == null) {
            binding = DataBindingUtil.setContentView(this, xml)
            //hide keyboard
            UIUtils.addKeyboardEvents(this, binding!!.root, binding!!.root)
        }

        //init progress dialog
        createProgressDialog()

        //init
        initFragmentManager()
        initUI()

        //check and load intent params
        if (intent != null && intent.extras != null) {
            loadPassedParamsIfNeeded(intent.extras!!)
        }

        initData()
        initListener()
    }

    override fun attachBaseContext(newBase: Context) {
        //attach base context for calligraphy font
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onStart() {
        super.onStart()
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onDestroy() {
        if (binding != null) {
            UIUtils.removeKeyboardEvents(binding!!.root)
        }

        for (dialog in setOfDialogs) {
            dialog.dismiss()
        }

        unRegisterReceiver()

        super.onDestroy()
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
        myFragmentManager!!.addOnBackStackChangedListener(this)
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
    fun setFragment(tag: String, data: Any?, isAddBackStack: Boolean = true) {
        val fragmentPopped = myFragmentManager!!.popBackStackImmediate(tag, 0)
        if (!fragmentPopped) {
            val fragment = getFragment(tag, data)
            if (fragment != null) {
                val transaction = myFragmentManager!!.beginTransaction()
                transaction.setCustomAnimations(
                    R.anim.slide_from_right,
                    R.anim.slide_to_left,
                    R.anim.slide_from_left,
                    R.anim.slide_to_right
                )
                transaction.replace(R.id.content_fragment, fragment, tag)
                if (isAddBackStack)
                    transaction.addToBackStack(tag)
                transaction.commit()
            } else
                Logger.e("Forgot add fragment into base activity!")
        }
    }

    //get fragment by tag and data
    private fun getFragment(tag: String, data: Any?): BaseFragment? {
        /*if (Fragment1.class.getName().equals(tag)) {
            return new Fragment1();
        } */
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
    fun startActivity(tag: String, bundle: Bundle?, isFinish: Boolean = true) {
        val intent = getIntentActivity(tag)

        if (intent != null) {
            if (bundle != null) {
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

        if (intent != null) {
            if (bundle != null) {
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

        if (intent != null) {
            if (bundle != null) {
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
        if (!TextUtils.isEmpty(tag)) {
            if (tag == MainActivity::class.java.name) {
                intent = MainActivity.createIntent(this)
            } else if (tag == TourActivity::class.java.name) {
                intent = TourActivity.createIntent(this)
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
        progressDialog = Dialog(this, R.style.DialogFullScreen)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.process_dialog, null)
        //show view
        val view = dialogView.findViewById<View>(R.id.progressBar)
        view.visibility = View.VISIBLE

        progressDialog!!.setContentView(dialogView)
        progressDialog!!.setCancelable(false)

        val dialogWindow = progressDialog!!.window
        dialogWindow?.let {
            dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialogWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }

        addDialog(progressDialog)
    }

    /**
     * Show progress layout
     */
    fun showProgress() {
        progressDialog?.show()
    }

    /**
     * Hide progress layout
     */
    fun hideProgress() {
        progressDialog?.hide()
    }
}
/**
 * set fragment for current activity
 *
 * @param tag  tag name
 * @param data data
 */
/**
 * start activity and finish current activity
 *
 * @param tag    tag name
 * @param bundle bundle
 */
