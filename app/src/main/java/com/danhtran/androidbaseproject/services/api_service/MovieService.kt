package com.danhtran.androidbaseproject.services.api_service

import com.danhtran.androidbaseproject.services.api_config.APIServer
import com.danhtran.androidbaseproject.services.extras.RxScheduler
import com.danhtran.androidbaseproject.services.model.ResponseModel
import io.reactivex.Observable

/**
 * Created by danhtran on 2/20/2018.
 */

class MovieService(private val apiServer: APIServer) {
    fun usersRepositories(): Observable<ResponseModel<String>> {
        return apiServer.getSkillTags()
            .compose(RxScheduler.applyIoSchedulers())
    }
}
