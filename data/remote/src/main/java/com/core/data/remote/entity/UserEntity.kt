package com.core.data.remote.entity

/**
User name and surname.
User email.
User picture.
User phone.
User gender.
User location: street, city and state.
Registered date.
 */
data class UserEntity(
    var gender: String? = null,
    var name: NameEntity? = null,
    var location: LocationEntity? = null,
    var email: String? = null,
    var login: LoginEntity? = null,
    var registered: RegisteredEntity? = null,
    var phone: String? = null,
    var picture: PictureEntity? = null
)

data class NameEntity(
    var title: String? = null,
    var first: String? = null,
    var last: String? = null
)

data class LocationEntity(
    var street: Street? = null,
    var city: String? = null,
    var state: String? = null,
    var county: String? = null,
    var postcode: String? = null,
    var coordinates: Coordinates? = null,
    var timezone: Timezone? = null
)

data class Street(
    var number: Int? = null,
    var name: String? = null
)

data class Coordinates(
    var latitude: String? = null,
    var longitude: String? = null
)

data class Timezone(
    var offset: String? = null,
    var description: String? = null
)

data class LoginEntity(
    var uuid: String? = null,
    var username: String? = null,
    var password: String? = null,
    var salt: String? = null,
    var md5: String? = null,
    var sha1: String? = null,
    var sha256: String? = null
)

data class RegisteredEntity(
    var date: String? = null,
    var age: Int? = null
)

data class PictureEntity(
    var large: String? = null,
    var medium: String? = null,
    var thumbnail: String? = null
)
