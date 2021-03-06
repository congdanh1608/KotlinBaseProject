package com.danhtran.androidbaseproject.di.module

import com.apollographql.apollo.ApolloClient
import com.danhtran.androidbaseproject.services.graphql_service.AuthenticationService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by danhtran on 2/27/2018.
 */
@Module
class GraphqlServiceModule {
    @Provides
    @Singleton
    internal fun getAuthenticationService(apolloClient: ApolloClient): AuthenticationService {
        return AuthenticationService(apolloClient)
    }
}
