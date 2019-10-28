package com.core.data.remote.retrofit

import com.core.data.remote.entity.UserEntity
import com.core.data.remote.AppGateway
import com.core.data.remote.retrofit.service.AppRetrofitService
import io.reactivex.Single

class AppRetrofitGateway(
    private val service: AppRetrofitService
) : AppGateway {

    override fun users(page: Int, results: Int): Single<List<UserEntity>> {
        return service.users(page, results).map { it.results }
    }
}
