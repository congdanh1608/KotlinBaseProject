package com.danhtran.androidbaseproject

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.multidex.MultiDexApplication
import com.danhtran.androidbaseproject.di.component.AppComponent
import com.danhtran.androidbaseproject.di.component.DaggerAppComponent
import com.danhtran.androidbaseproject.di.module.AppModule
import com.danhtran.androidbaseproject.utils.UIUtils
import com.livefront.bridge.Bridge
import com.livefront.bridge.SavedStateHandler
import com.orhanobut.hawk.Hawk
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import java.io.IOException
import java.net.SocketException


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

        //set theme
        UIUtils.setAppTheme()
    }


    //region init SDK
    private fun initSDK() {
        initBridge()
        initHawk()
        initFont()
        initLogger()
        initRxJavaErrorHandler()
//        initOneSignal()
    }

    //init fonts for app
    private fun initFont() {
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
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

    private fun initRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler { throwable: Throwable ->
            if (throwable is UndeliverableException) {
                throwable.cause?.let {
                    Thread.currentThread().uncaughtExceptionHandler?.uncaughtException(Thread.currentThread(), it)
                    return@setErrorHandler
                }
            }
            if (throwable is IOException || throwable is SocketException) {
                // fine, irrelevant network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (throwable is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if (throwable is NullPointerException || throwable is IllegalArgumentException) {
                // that's likely a bug in the application
                Thread.currentThread().uncaughtExceptionHandler?.uncaughtException(Thread.currentThread(), throwable)
                return@setErrorHandler
            }
            if (throwable is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().uncaughtExceptionHandler?.uncaughtException(Thread.currentThread(), throwable)
                return@setErrorHandler
            }
            Logger.w("Undeliverable exception received, not sure what to do ${throwable.message}")
        }
    }

    /* private fun initOneSignal(){
         OneSignal.enableVibrate(true);
         OneSignal.enableSound(true);
         OneSignal.setSubscription(true);
         OneSignal.startInit(this)
             .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
             .setNotificationOpenedHandler(MyNotificationOpenedHandler())
             .setNotificationReceivedHandler(MyNotificationReceivedHandler())
             .unsubscribeWhenNotificationsAreDisabled(true)
             .init()
     }*/

    //endregion init SDK


    //region init Data
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
    //endregion init Data


    companion object {
        private lateinit var myApplication: MyApplication

        fun instance(): MyApplication {
            return myApplication
        }
    }
}
