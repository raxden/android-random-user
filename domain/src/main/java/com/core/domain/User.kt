package com.core.domain

import org.threeten.bp.LocalDateTime

data class User(
    var id: String,
    var name: String,
    var surname: String,
    var email: String,
    var picture: Picture,
    var phone: String,
    var gender: Gender,
    var location: Location,
    var registered: LocalDateTime?
) {

    constructor(id: String) : this(
        id = id,
        name = "",
        surname = "",
        email = "",
        picture = Picture("", ""),
        phone = "",
        gender = Gender.MALE,
        location = Location("", "", "", ""),
        registered = null
    )
}

data class Picture(
    var large: String,
    var thumbnail: String
)

data class Location(
    var street: String,
    var number: String,
    var city: String,
    var state: String
)

enum class Gender {
    MALE, FEMALE
}

