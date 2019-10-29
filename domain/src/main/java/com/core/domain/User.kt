package com.core.domain

import org.threeten.bp.LocalDateTime

data class User(
    var id: String,
    var name: String,
    var surname: String,
    var email: String,
    var picture: String,
    var thumbnail: String,
    var phone: String,
    var gender: Gender,
    var street: String,
    var city: String,
    var state: String,
    var registered: LocalDateTime
)

enum class Gender {
    MALE, FEMALE
}

