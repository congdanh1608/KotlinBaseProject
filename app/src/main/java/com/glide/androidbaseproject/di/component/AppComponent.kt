package com.glide.androidbaseproject.di.component

import com.glide.androidbaseproject.MyApplication
import com.glide.androidbaseproject.di.module.*
import com.glide.androidbaseproject.ui.activity.main.MainActivityPresenter
import com.glide.androidbaseproject.ui.activity.splash.SplashActivity
import com.glide.androidbaseproject.ui.activity.tour.TourActivityPresenter
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
    fun inject(tourActivityPresenter: TourActivityPresenter)
    fun inject(mainActivityPresenter: MainActivityPresenter)


    //for subComponent
    fun plusRoomComponent(roomModule: RoomModule): RoomComponent
}