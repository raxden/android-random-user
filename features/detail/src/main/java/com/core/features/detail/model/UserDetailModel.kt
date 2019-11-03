package com.core.features.detail.model

import com.core.domain.Gender
import com.core.domain.User
import com.core.features.detail.R
import org.threeten.bp.format.DateTimeFormatter

data class UserDetailModel(
    val name: String,
    val surname: String,
    val email: String,
    val image: String,
    val gender: Int,
    val genderImage: Int,
    val phone: String,
    val registeredDate: String,
    val street: String,
    val city: String,
    val state: String
) {

    constructor(user: User) : this(
        user.name,
        user.surname,
        user.email,
        user.picture.large,
        when (user.gender) {
            Gender.MALE -> R.string.detail_male
            else -> R.string.detail_female
        },
        when (user.gender) {
            Gender.MALE -> R.drawable.ic_male
            else -> R.drawable.ic_female
        },
        user.phone,
        user.registered?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) ?: "",
        user.location.street,
        user.location.city,
        user.location.state
    )
}

