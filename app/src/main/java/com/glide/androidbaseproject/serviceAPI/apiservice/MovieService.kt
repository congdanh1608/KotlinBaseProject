package com.glide.androidbaseproject.serviceAPI.apiservice

import com.glide.androidbaseproject.appmodel.Movie
import com.glide.androidbaseproject.serviceAPI.apiconfig.APIServer
import com.glide.androidbaseproject.serviceAPI.extras.RxScheduler
import com.glide.androidbaseproject.serviceAPI.model.ResponseModel
import io.reactivex.Observable

/**
 * Created by danhtran on 2/20/2018.
 */

class MovieService(private val apiServer: APIServer) {

    val usersRepositories: Observable<ResponseModel<List<Movie>>>
        get() = apiServer.movies
            .compose(RxScheduler.applyIoSchedulers())
}
