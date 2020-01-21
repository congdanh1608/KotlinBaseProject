package com.danhtran.androidbaseproject

import androidx.multidex.MultiDexApplication
import com.danhtran.androidbaseproject.di.component.AppComponent
import com.danhtran.androidbaseproject.di.component.DaggerAppComponent
import com.danhtran.androidbaseproject.di.module.AppModule
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


/**
 * Created by danhtran on 2/25/2018.
 */

class MyApplication : MultiDexApplication() {
    lateinit var appComponent: AppComponent
        private set
    var token: String? = null

    init {
        myApplication = this
    }

    override fun onCreate() {
        super.onCreate()
        initSDK()
        initData()
    }

    private fun initSDK() {
        //        initHawk();
        //        initFacebook();
        initFont()
        initLogger()
        //        initEvernoteState();
    }

    /*private void initFacebook() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }*/

    //init fonts for app
    private fun initFont() {
        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Helvetica.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }

    private fun initLogger() {
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    private fun initEvernoteState() {
        //        StateSaver.setEnabledForAllActivitiesAndSupportFragments(this, true);
    }

    private fun initHawk() {
        Hawk.init(this).build()
    }

    private fun initData() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        appComponent.inject(this)

        //language
        //        String s = SharedPrefsHelper.getInstance().readString(SharePref.LANGUAGE.toString());
        //        if (s != null) lang = s;
        //        else lang = Locale.getDefault().getLanguage();

        //load token
        //        session = SharedPrefsHelper.getInstance().readObject(Session.class, SharePref.SESSION_LOGIN.toString());

        //secret key
        //        Utils.generalSHAKey(this);
    }

    companion object {
        private lateinit var myApplication: MyApplication

        fun instance(): MyApplication {
            return myApplication
        }
    }
}
