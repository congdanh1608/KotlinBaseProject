package com.danhtran.androidbaseproject.di.module

import com.danhtran.androidbaseproject.services.api_config.APIServer
import com.danhtran.androidbaseproject.services.api_service.MovieService
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
