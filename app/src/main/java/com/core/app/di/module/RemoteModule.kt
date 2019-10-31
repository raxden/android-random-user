package com.core.app.di.module

import android.content.Context
import com.core.app.BuildConfig
import com.core.app.R
import com.core.data.remote.retrofit.adapter.RxErrorHandlingCallAdapterFactory
import com.core.data.remote.UserDataSource
import com.core.data.remote.retrofit.service.UserService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object RemoteModule {

    private const val TIMEOUT = 35                          // 30 sec
    private const val CONNECTION_TIMEOUT = 15               // 10 sec

    @JvmStatic
    @Provides
    @Singleton
    internal fun gson(): Gson = GsonBuilder().create()

    @JvmStatic
    @Provides
    @Singleton
    internal fun httpLoggingInterceptorLevel(): HttpLoggingInterceptor.Level = when {
        BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
        else -> HttpLoggingInterceptor.Level.NONE
    }

    // Interceptors ================================================================================

    @JvmStatic
    @Provides
    @Singleton
    internal fun httpLoggingInterceptor(
        level: HttpLoggingInterceptor.Level
    ): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = level
    }


    // OKHttpClient ================================================================================

    @JvmStatic
    @Provides
    @Singleton
    internal fun httpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor)
        .retryOnConnectionFailure(true)
        .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        .connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .build()

    // Retrofit ====================================================================================

    @JvmStatic
    @Provides
    @Singleton
    internal fun retrofit(
        context: Context,
        httpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(context.getString(R.string.data_api_domain))
        .client(httpClient)
        .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    // Retrofit services ===========================================================================

    @JvmStatic
    @Provides
    @Singleton
    internal fun userService(
        retrofit: Retrofit
    ): UserService = retrofit.create(UserService::class.java)

    // Data sources ================================================================================

    @JvmStatic
    @Provides
    @Singleton
    internal fun userDataSource(
        service: UserService
    ): UserDataSource = UserDataSource(service)
}
