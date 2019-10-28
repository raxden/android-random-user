package com.core.domain.repository

import com.core.domain.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {

    fun list(page: Int, results: Int): Single<List<User>>

    fun exclude(user: User): Completable
}