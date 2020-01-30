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
import com.danhtran.androidbaseproject.extras.Constant
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
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
            .serverUrl(BuildConfig.serverUrl)
            .okHttpClient(okHttpClient)
            .normalizedCache(lruNormalizedCacheFactory, cacheKeyResolver)
            //.subscriptionTransportFactory(WebSocketSubscriptionTransport.Factory(SUBSCRIPTION_BASE_URL, okHttpClient))
            .build()
    }

    @Provides
    @Singleton
    fun provOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val authHeader = "Bearer \$accessTokenId"
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(original.method, original.body)
                builder.header("Authorization", authHeader)
                chain.proceed(builder.build())
            }.build()
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
}