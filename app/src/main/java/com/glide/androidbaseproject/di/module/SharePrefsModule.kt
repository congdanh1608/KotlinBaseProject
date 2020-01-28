package com.glide.androidbaseproject.di.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.glide.androidbaseproject.database.share_preferences.SharedPrefsHelper
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

/**
 * Created by danhtran on 2/27/2018.
 */
@Module
class SharePrefsModule {
    @Singleton
    @Provides
    fun getSharedPrefsHelper(sharedPreferences: SharedPreferences): SharedPrefsHelper {
        return SharedPrefsHelper(sharedPreferences)
    }

    @Singleton
    @Provides
    fun getSharedPreferences(context: Context): SharedPreferences {
        val sharedPreferences: SharedPreferences
        //encrypt for release version - min api 23
        //        if (BuildConfig.DEBUG) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        /*} else {
            sharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );*/

        return sharedPreferences
    }
}
