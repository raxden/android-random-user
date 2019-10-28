package com.core.data.repository.mapper

import com.core.common.android.DataMapper
import com.core.data.remote.entity.UserEntity
import com.core.domain.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserEntityDataMapper @Inject constructor(): DataMapper<UserEntity, User>() {

    override fun transform(source: UserEntity) = User(
        id = source.login?.uuid ?: ""
    )

    override fun inverse(source: User): UserEntity = UserEntity()
}