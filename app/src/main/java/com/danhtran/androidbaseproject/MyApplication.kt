package com.danhtran.androidbaseproject

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.multidex.MultiDexApplication
import com.danhtran.androidbaseproject.di.component.AppComponent
import com.danhtran.androidbaseproject.di.component.DaggerAppComponent
import com.danhtran.androidbaseproject.di.module.AppModule
import com.livefront.bridge.Bridge
import com.livefront.bridge.SavedStateHandler
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
        initBridge()
        initHawk()
        initFont()
        initLogger()
    }

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

    private fun initHawk() {
        Hawk.init(this).build()
    }

    private fun initBridge() {
        Bridge.initialize(applicationContext, object : SavedStateHandler {
            override fun saveInstanceState(@NonNull target: Any, @NonNull state: Bundle) {
                //register other state saving libraries here
            }

            override fun restoreInstanceState(@NonNull target: Any, @Nullable state: Bundle?) {
                //register other state saving libraries here
            }
        })
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
