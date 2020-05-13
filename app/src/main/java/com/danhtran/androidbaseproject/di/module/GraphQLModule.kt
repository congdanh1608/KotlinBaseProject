package com.danhtran.androidbaseproject.di.module

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore
import com.apollographql.apollo.cache.normalized.CacheKey
import com.apollographql.apollo.cache.normalized.CacheKeyResolver
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.danhtran.androidbaseproject.BuildConfig
import com.danhtran.androidbaseproject.MyApplication
import com.danhtran.androidbaseproject.extras.Constant
import com.danhtran.androidbaseproject.extras.enums.Header
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by DanhTran on 1/28/2020.
 */
@Module
class GraphQLModule {
    @Provides
    @Singleton
    fun provApolloClient(
        okHttpClient: OkHttpClient,
        lruNormalizedCacheFactory: LruNormalizedCacheFactory,
        cacheKeyResolver: CacheKeyResolver
    ): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(BuildConfig.graphQLServer)
            .okHttpClient(okHttpClient)
            //.normalizedCache(lruNormalizedCacheFactory, cacheKeyResolver)     //disable cache
            //.subscriptionTransportFactory(WebSocketSubscriptionTransport.Factory(SUBSCRIPTION_BASE_URL, okHttpClient))
            .build()
    }

    @Provides
    @Singleton
    fun provOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val builder = OkHttpClient.Builder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(interceptor)

        if (MyApplication.instance().token != null) {
            val authHeader = "${Header.TypeHeader.BEARER.value} ${MyApplication.instance().token}"
            builder.addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(original.method, original.body)
                builder.header(Header.HeaderValue.AUTHORIZATION.value, authHeader)
                chain.proceed(builder.build())
            }
        }


        return builder.build()
    }

    @Provides
    @Singleton
    fun provLruNormalizedCacheFactory(): LruNormalizedCacheFactory {
        return LruNormalizedCacheFactory(
            EvictionPolicy.builder()
                .maxSizeBytes((10 * 1024).toLong())
                .build()
        )
    }

    @Provides
    @Singleton
    fun provDiskLruHttpCacheStore(): DiskLruHttpCacheStore {
        val file = File(Constant.CACHE)
        val size = (1024 * 1024).toLong()
        return DiskLruHttpCacheStore(file, size)
    }

    @Provides
    @Singleton
    fun provCacheKeyResolver(): CacheKeyResolver {
        return object : CacheKeyResolver() {
            override fun fromFieldRecordSet(
                field: ResponseField,
                recordSet: Map<String, Any>
            ): CacheKey {
                return formatCacheKey(recordSet["id"] as String?)
            }

            override fun fromFieldArguments(
                field: ResponseField,
                variables: Operation.Variables
            ): CacheKey {
                return formatCacheKey(field.resolveArgument("id", variables) as String?)
            }

            private fun formatCacheKey(id: String?): CacheKey {
                return if (id == null || id.isEmpty()) {
                    CacheKey.NO_KEY
                } else {
                    CacheKey.from(id)
                }
            }
        }
    }

    companion object {
        private val REQUEST_TIMEOUT = 60
        private val READ_TIMEOUT = 60
    }

}