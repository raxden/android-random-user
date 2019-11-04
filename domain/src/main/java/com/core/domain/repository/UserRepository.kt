package com.core.domain.repository

import com.core.domain.User
import io.reactivex.Completable
import io.reactivex.Maybe

interface UserRepository {

    fun list(page: Int, results: Int): Maybe<List<User>>

    fun exclude(id: String): Completable
}