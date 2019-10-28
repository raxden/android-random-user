package com.core.data.remote.retrofit.service

import com.core.data.remote.entity.ResultsEntity
import com.core.data.remote.entity.UserEntity
import io.reactivex.Single
import retrofit2.http.*

interface AppRetrofitService {

    @GET("/?inc=name,email,picture,phone,gender,location,registered,login")
    fun users(
        @Query("page") page: Int,
        @Query("results") results: Int
    ): Single<ResultsEntity<UserEntity>>
}
