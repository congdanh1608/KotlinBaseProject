package com.danhtran.androidbaseproject.di.component

import com.danhtran.androidbaseproject.MyApplication
import com.danhtran.androidbaseproject.di.module.*
import com.danhtran.androidbaseproject.ui.activity.main.MainActivityVM
import com.danhtran.androidbaseproject.ui.activity.splash.SplashActivity
import dagger.Component

import javax.inject.Singleton

/**
 * Created by danhtran on 2/25/2018.
 */
@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, GraphQLModule::class, GraphqlServiceModule::class, APIServiceModule::class, SharePrefsModule::class])
interface AppComponent {
    //inject where you want to inject to get providers.
    fun inject(myApplication: MyApplication)

    fun inject(splashActivity: SplashActivity)
    fun inject(mainActivityVM: MainActivityVM)


    //for subComponent
    fun plusRoomComponent(roomModule: RoomModule): RoomComponent
}
