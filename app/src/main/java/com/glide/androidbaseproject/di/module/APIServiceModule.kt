package com.glide.androidbaseproject.di.module

import com.glide.androidbaseproject.serviceAPI.apiconfig.APIServer
import com.glide.androidbaseproject.serviceAPI.apiservice.MovieService
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

/**
 * Created by danhtran on 2/27/2018.
 */
@Module
class APIServiceModule {
    @Provides
    @Singleton
    internal fun getMovieService(apiServer: APIServer): MovieService {
        return MovieService(apiServer)
    }
}
