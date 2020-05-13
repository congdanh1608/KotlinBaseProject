package com.danhtran.androidbaseproject.ui.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.danhtran.androidbaseproject.R
import com.danhtran.androidbaseproject.databinding.ActivityMainBinding
import com.danhtran.androidbaseproject.databinding.NavHeaderMainBinding
import com.danhtran.androidbaseproject.extras.Constant
import com.danhtran.androidbaseproject.ui.activity.BaseActivityViewModel
import com.danhtran.androidbaseproject.ui.activity.BaseAppCompatActivity
import com.google.android.material.navigation.NavigationView

class MainActivity : BaseAppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    private val mainActivityVM: MainActivityVM by viewModels { MainActivityVMFactory(this) }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var navController: NavController
    private lateinit var headerBinding: NavHeaderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //load deep link when app is running
        onNewIntent(intent)
    }

    override fun loadPassedParamsIfNeeded(extras: Bundle) {
        super.loadPassedParamsIfNeeded(extras)
        //load deep link when app is not running

    }

    //load deep link data onNewIntent()
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val action = intent.action
        val data = intent.dataString
        if (Intent.ACTION_VIEW == action && data != null) {
            val type = data.substring(data.lastIndexOf("/") + 1)
            if (!TextUtils.isEmpty(type)) {
                //do something
            }
        }
    }

    override fun setLayout(): Int {
        return R.layout.activity_main
    }

    override fun initUI() {
        mBinding = binding as ActivityMainBinding

//        initNavDrawerMenu()
    }

    override fun initViewModel(): BaseActivityViewModel? {
        return mainActivityVM
    }

    override fun initData() {
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mainActivityVM
    }

    override fun initListener() {
        //nav left event
        /*headerBinding.btnClose.setOnClickListener {
            closeNavLeftView()
        }*/
    }

    override fun onBackPressed() {
        if (closeNavLeftView()) {       //check close left nav drawer
            return
        }
        val count = myFragmentManager!!.backStackEntryCount     //fragment
        if (count <= 1 && navController.graph.startDestination == navController.currentDestination?.id) {
            exitApp()
        } else {
            super.onBackPressed()
        }
    }

    override fun setTitle(title: CharSequence?) {
        mBinding.appBarMain.toolbarTitle.text = title
    }

    //region Navigation drawer menu
    /*private fun initNavDrawerMenu() {
        drawerLayout = mBinding.drawerLayout

        //Left drawer menu
        navView = mBinding.navView
        navController = findNavController(R.id.nav_host_fragment)

        val headerView = navView.getHeaderView(0)
        headerBinding = NavHeaderMainBinding.bind(headerView)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_setting
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        //set title listener
        navController.addOnDestinationChangedListener { _, destination, _ ->
            run {
                title = destination.label
            }
        }
        //add after setupWithNavController
        //manual handle other nav controller
        navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {

            val handled = NavigationUI.onNavDestinationSelected(it, navController)

            if (!handled) {
                // handle other navigation other than default
                when (it.itemId) {
                    *//*R.id.nav_feedback -> {

                    }*//*
                }
            }

            drawerLayout.closeDrawer(GravityCompat.START)

            return@OnNavigationItemSelectedListener handled
        })
    }*/

    override fun onSupportNavigateUp(): Boolean {
        /*val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) ||*/
        return super.onSupportNavigateUp()
    }

    private fun closeNavLeftView(): Boolean {
        /* if (drawerLayout.isDrawerOpen(navView)) {
             drawerLayout.closeDrawer(navView)
             return true
         }*/
        return false
    }
    //endregion Navigation drawer menu

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constant.REQUEST_CODE_RESULT_LOCATION_ON_OFF -> {
            }
            Constant.REQUEST_CODE_RESULT_LOCATION_PERMISSION -> {
            }
        }
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
