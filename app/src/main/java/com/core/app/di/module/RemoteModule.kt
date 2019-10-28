package com.core.app.di.module

import android.content.Context
import com.core.app.BuildConfig
import com.core.app.R
import com.core.data.remote.AppGateway
import com.core.data.remote.retrofit.AppRetrofitGateway
import com.core.data.remote.retrofit.service.AppRetrofitService
import com.core.data.remote.stream.AppFileStreamGateway
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
object RemoteModule {

    private const val TIMEOUT = 35                          // 30 sec
    private const val CONNECTION_TIMEOUT = 15               // 10 sec

    @JvmStatic
    @Provides
    @Singleton
    @Named("excludeFieldsWithoutExposeAnnotation")
    internal fun excludeFieldsWithoutExposeAnnotationGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @JvmStatic
    @Provides
    @Singleton
    internal fun httpLoggingInterceptorLevel(): HttpLoggingInterceptor.Level {
        return if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }

    // Interceptors ====================================================================================================

    @JvmStatic
    @Provides
    @Singleton
    internal fun httpLoggingInterceptor(
        logginLevel: HttpLoggingInterceptor.Level
    ): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = logginLevel
        }
    }

    // OKHttpClient ====================================================================================================

    @JvmStatic
    @Provides
    @Singleton
    internal fun httpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .retryOnConnectionFailure(true)
            .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
            .connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .build()
    }

    // Retrofit ========================================================================================================

    @JvmStatic
    @Provides
    @Singleton
    internal fun retrofit(
        context: Context,
        httpClient: OkHttpClient,
        @Named("excludeFieldsWithoutExposeAnnotation") gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.data_api_domain))
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    // RetrofitService =================================================================================================

    @JvmStatic
    @Provides
    @Singleton
    internal fun appRetrofitService(retrofit: Retrofit): AppRetrofitService {
        return retrofit.create(AppRetrofitService::class.java)
    }

    // Gateway =========================================================================================================

    @JvmStatic
    @Provides
    @Singleton
    internal fun appGateway(
        @Named("excludeFieldsWithoutExposeAnnotation") gson: Gson,
        service: AppRetrofitService
    ): AppGateway {
        return when {
            BuildConfig.FLAVOR == "mock" -> AppFileStreamGateway(gson)
            else -> AppRetrofitGateway(service)
        }
    }
}
