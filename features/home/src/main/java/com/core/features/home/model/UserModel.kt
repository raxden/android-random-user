package com.core.features.home.model

import com.core.domain.User

class UserModel(
    val id: String,
    val name: String,
    val email: String,
    val thumbnail: String,
    val phone: String
) {

    constructor(user: User) : this(
        user.id,
        user.name + " " + user.surname,
        user.email,
        user.picture.thumbnail,
        user.phone
    )
}