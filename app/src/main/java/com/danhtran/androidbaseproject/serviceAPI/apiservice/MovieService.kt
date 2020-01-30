package com.danhtran.androidbaseproject.serviceAPI.apiservice

import com.danhtran.androidbaseproject.appmodel.Movie
import com.danhtran.androidbaseproject.serviceAPI.apiconfig.APIServer
import com.danhtran.androidbaseproject.serviceAPI.extras.RxScheduler
import com.danhtran.androidbaseproject.serviceAPI.model.ResponseModel
import io.reactivex.Observable

/**
 * Created by danhtran on 2/20/2018.
 */

class MovieService(private val apiServer: APIServer) {

    val usersRepositories: Observable<ResponseModel<List<Movie>>>
        get() = apiServer.movies
            .compose(RxScheduler.applyIoSchedulers())
}
