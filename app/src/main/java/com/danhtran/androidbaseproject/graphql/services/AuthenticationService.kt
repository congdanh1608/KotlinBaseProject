package com.danhtran.androidbaseproject.graphql.services

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo
import com.danhtran.androidbaseproject.LanguagesQuery
import com.danhtran.androidbaseproject.SignInMutation
import com.danhtran.androidbaseproject.serviceAPI.extras.RxScheduler
import io.reactivex.Observable

/**
 * Created by DanhTran on 1/28/2020.
 */

class AuthenticationService(private val apolloClient: ApolloClient) {

    fun getLanguages(): Observable<Response<LanguagesQuery.Data>> {
        val query = LanguagesQuery.builder().build()
        return Rx2Apollo.from(
            apolloClient.query(
                query
            )
        ).compose(RxScheduler.applyIoSchedulers())
    }


    fun signIn(): Observable<Response<SignInMutation.Data>>? {
        val mutation = SignInMutation.builder()
            .email("john.terry3@gmail.com")
            .pass("Abc123456@")
            .build()
        return Rx2Apollo.from(apolloClient.mutate(mutation))
            .compose(RxScheduler.applyIoSchedulers())
    }
}