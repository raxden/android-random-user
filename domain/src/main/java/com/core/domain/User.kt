package com.core.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDateTime

@Parcelize
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
): Parcelable {

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

@Parcelize
data class Picture(
    var large: String,
    var thumbnail: String
): Parcelable

@Parcelize
data class Location(
    var street: String,
    var number: String,
    var city: String,
    var state: String
): Parcelable

enum class Gender {
    MALE, FEMALE
}

