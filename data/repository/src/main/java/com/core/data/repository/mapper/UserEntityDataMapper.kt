package com.core.data.repository.mapper

import com.core.common.android.DataMapper
import com.core.common.android.extensions.toLocalDateTime
import com.core.data.remote.entity.UserEntity
import com.core.domain.Gender
import com.core.domain.Picture
import com.core.domain.Location
import com.core.domain.User
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserEntityDataMapper @Inject constructor() : DataMapper<UserEntity, User>() {

    override fun transform(source: UserEntity) = User(
        id = source.login?.uuid ?: "",
        name = source.name?.first?.trim() ?: "",
        surname = source.name?.last?.trim() ?: "",
        email = source.email ?: "",
        picture = Picture(
            source.picture?.large ?: "",
            source.picture?.medium?: ""
        ),
        phone = source.phone ?: "",
        gender = when (source.gender) {
            "male" -> Gender.MALE
            else -> Gender.FEMALE
        },
        location = Location(
            source.location?.street?.name ?: "",
            source.location?.street?.number.toString(),
            source.location?.city ?: "",
            source.location?.state ?: ""
        ),
        registered = source.registered?.date?.toLocalDateTime(DateTimeFormatter.ISO_DATE_TIME)
    )

    override fun inverse(source: User): UserEntity = UserEntity()
}