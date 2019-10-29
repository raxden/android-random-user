package com.core.data.repository.mapper

import com.core.common.android.DataMapper
import com.core.common.android.extensions.toLocalDateTime
import com.core.data.remote.entity.UserEntity
import com.core.domain.Gender
import com.core.domain.User
import org.threeten.bp.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserEntityDataMapper @Inject constructor() : DataMapper<UserEntity, User>() {

    override fun transform(source: UserEntity) = User(
        id = source.login?.uuid ?: "",
        name = source.name?.first ?: "",
        surname = source.name?.last ?: "",
        email = source.email ?: "",
        picture = source.picture?.large ?: "",
        thumbnail = source.picture?.thumbnail ?: "",
        phone = source.phone ?: "",
        gender = when (source.gender) {
            "male" -> Gender.MALE
            else -> Gender.FEMALE
        },
        street = "${source.location?.street?.name} - ${source.location?.street?.number}",
        city = source.location?.city ?: "",
        state = source.location?.state ?: "",
        registered = source.registered?.date?.toLocalDateTime("yyyy-MM-dd HH:mm:ss")
            ?: LocalDateTime.now()
    )

    override fun inverse(source: User): UserEntity = UserEntity()
}