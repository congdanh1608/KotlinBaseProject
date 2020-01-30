package com.danhtran.androidbaseproject.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

/**
 * Created by danhtran on 2/25/2018.
 */

@Module
class AppModule(
    @get:Singleton
    @get:Provides
    val application: Application
) {

    val applicationContext: Context
        @Singleton
        @Provides
        get() = application.applicationContext
}
