package com.core.data.remote

import com.core.data.remote.entity.UserEntity
import io.reactivex.Single

interface AppGateway {

    fun users(page: Int, results: Int): Single<List<UserEntity>>
}
