package com.ncl.app.network

import ApiService.Companion.ACCEPT_JSON
import com.google.gson.GsonBuilder
import com.ncl.app.base.BaseApplication
import com.ncl.app.utils.baseUrl
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object ServiceBuilder {

    private const val HEADER_CACHE_CONTROL = "Cache-Control"
    const val ACCEPT_KEY = "Accept"
    const val AUTHORIZATION_KEY = "Authorization"
    private const val CONTENT_TYPE_KEY = "Content-Type"

    //    private const val CACHE_FILE_NAME = BuildConfig.cacheFileName
    private const val cacheSize = 10 * 1024 * 1024 // 10 MB
        .toLong()

    private val client = OkHttpClient.Builder()
        .cache(cache())
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(offlineInterceptor()!!)
//        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//        .addNetworkInterceptor(StethoInterceptor())
        .build()

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
//        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }

    private fun cache(): Cache? {
        return Cache(
            File(BaseApplication.getInstance()?.cacheDir, "CACHE_FILE_NAME"),
            cacheSize
        )
    }

    /**
     * This interceptor will be called both if the network is available and if the network is not available
     */
    private fun offlineInterceptor(): Interceptor? {
        return Interceptor { chain ->
            var request: Request = chain.request()
//            if (!isNetworkConnected(BaseApplication.getInstance()!!)) {
            val cacheControl = CacheControl.Builder()
                .maxStale(30, TimeUnit.DAYS)
                .build()
            request = request.newBuilder()
                .removeHeader(HEADER_CACHE_CONTROL)
                .addHeader(ACCEPT_KEY, ACCEPT_JSON)
                .header(ACCEPT_KEY, ACCEPT_JSON)
                .cacheControl(cacheControl)
                .build()
//            }
            chain.proceed(request)
        }
    }

    /**
     * This interceptor will be called only if the network is available
     */
    private fun networkInterceptor(): Interceptor? {
        return Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
//                .addHeader("X-Auth-Token", SyncStateContract.Constants.LEAGUE_API_KEY)
                .build()
            val response = chain.proceed(request)
            val cacheControl = CacheControl.Builder()
                .maxAge(5, TimeUnit.MINUTES)
                .build()
            response.newBuilder()
                .removeHeader(HEADER_CACHE_CONTROL)
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()
        }
    }
}