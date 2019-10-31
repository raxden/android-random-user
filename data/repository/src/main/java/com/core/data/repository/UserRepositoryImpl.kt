package com.core.data.repository

import com.core.data.local.dao.ExcludedUserDao
import com.core.data.local.model.ExcludedUserDB
import com.core.data.remote.UserDataSource
import com.core.data.remote.entity.UserEntity
import com.core.data.repository.mapper.UserEntityDataMapper
import com.core.domain.User
import com.core.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserRepositoryImpl @Inject internal constructor(
    private val userDataSource: UserDataSource,
    private val excludedUserDao: ExcludedUserDao,
    private val mapper: UserEntityDataMapper
) : UserRepository {

    override fun list(page: Int, results: Int): Maybe<List<User>> {
        return Single.zip(
            userDataSource.users(page, results)
                .map { it.distinctBy { entity -> entity.login?.uuid } }
                .observeOn(Schedulers.io()),
            excludedUserDao.findAll()
                .observeOn(Schedulers.io()),
            BiFunction<List<UserEntity>, List<ExcludedUserDB>, List<User>> { users, excludedUsers ->
                mapper.transform(users.filter { user ->
                    excludedUsers.find { it.id == user.login?.uuid }?.let { false } ?: true
                })
            }
        ).filter { it.isNotEmpty() }
    }

    override fun exclude(id: String): Completable {
        return excludedUserDao.insert(ExcludedUserDB(id))
    }
}