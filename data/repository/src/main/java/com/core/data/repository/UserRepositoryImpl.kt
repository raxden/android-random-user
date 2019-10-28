package com.core.data.repository

import com.core.data.remote.AppGateway
import com.core.data.repository.mapper.UserEntityDataMapper
import com.core.domain.User
import com.core.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject internal constructor(
    private val gateway: AppGateway,
    private val mapper: UserEntityDataMapper
) : UserRepository {

    override fun list(page: Int, results: Int): Single<List<User>> {
        return gateway.users(page, results)
            .map { mapper.transform(it) }
    }
}