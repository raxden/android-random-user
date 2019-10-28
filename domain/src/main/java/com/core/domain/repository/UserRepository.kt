package com.core.domain.repository

import com.core.domain.User
import io.reactivex.Single

interface UserRepository {

    fun list(page: Int, results: Int): Single<List<User>>
}