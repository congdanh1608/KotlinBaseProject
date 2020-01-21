package com.danhtran.androidbaseproject.di.module

import android.content.Context
import com.danhtran.androidbaseproject.BuildConfig
import com.danhtran.androidbaseproject.MyApplication
import com.danhtran.androidbaseproject.R
import com.danhtran.androidbaseproject.extras.enums.Header
import com.danhtran.androidbaseproject.serviceAPI.apiconfig.APIServer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.halcyon.logger.HttpLogInterceptor
import com.halcyon.logger.ILogger
import com.orhanobut.logger.Logger
import dagger.Module
import dagger.Provides
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

import javax.inject.Named
import javax.inject.Singleton
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by danhtran on 2/27/2018.
 */
@Module
class NetworkModule {

    val interceptor: HttpLogInterceptor
        @Singleton
        @Provides
        get() = HttpLogInterceptor(ILogger { msg ->
            if (BuildConfig.DEBUG) {
                Logger.d(msg)
            }
        })

    @Provides
    @Singleton
    fun getAPIServer(retrofit: Retrofit): APIServer {
        return retrofit.create(APIServer::class.java)
    }

    //default Retrofit with cached
    @Singleton
    @Provides
    fun getRetrofitNonCached(@Named("non_cached") okHttpClient: OkHttpClient, context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.domainAPI))
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())          //for null key in response
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Named("cached")
    @Provides
    fun getRetrofitWithCached(@Named("cached") okHttpClient: OkHttpClient, context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.domainAPI))
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())          //for null key in response
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Named("non_headers")
    @Provides
    fun getRetrofitNonHeaders(@Named("non_headers") okHttpClient: OkHttpClient, context: Context): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .disableHtmlEscaping().create()
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.domainAPI))
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())          //for null key in response
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Named("non_cached")
    @Provides
    fun getOkHttpClient(interceptor: HttpLogInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val newRequest = chain.request().newBuilder()
                        .addHeader(
                            Header.HeaderValue.AUTHORIZATION.toString(),
                            Header.TypeHeader.BEARER.toString() + MyApplication.instance().token!!
                        )
                        .build()
                    return chain.proceed(newRequest)
                }
            })
            .build()
    }

    @Provides
    @Named("cached")
    @Singleton
    internal fun provideOkHttpClient(cache: Cache, httpLogInterceptor: HttpLogInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(httpLogInterceptor)
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val newRequest = chain.request().newBuilder()
                        .addHeader(
                            Header.HeaderValue.AUTHORIZATION.toString(),
                            Header.TypeHeader.BEARER.toString() + MyApplication.instance().token!!
                        )
                        .build()
                    return chain.proceed(newRequest)
                }
            })
            .build()
    }

    @Singleton
    @Named("non_headers")
    @Provides
    fun getOkHttpClientNonHeaders(interceptor: HttpLogInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val builder = chain.request().newBuilder()
                    return chain.proceed(builder.build())
                }
            })
            .build()
    }

    @Provides
    @Singleton
    internal fun provideCache(myApplication: MyApplication): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB for cache
        return Cache(myApplication.cacheDir, cacheSize.toLong())
    }

    companion object {
        private val REQUEST_TIMEOUT = 60
        private val READ_TIMEOUT = 60
    }

}
