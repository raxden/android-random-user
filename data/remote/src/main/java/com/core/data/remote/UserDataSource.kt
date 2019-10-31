package com.core.data.remote

import com.core.data.remote.entity.UserEntity
import com.core.data.remote.retrofit.service.UserService
import io.reactivex.Single

class UserDataSource(
    private val service: UserService
) {

    fun users(page: Int, results: Int): Single<List<UserEntity>> {
        return service.users(page, results).map { it.results }
    }
}
