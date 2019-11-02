package com.core.features.home.model

import androidx.recyclerview.widget.DiffUtil
import com.core.domain.User

data class UserModel(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val thumbnail: String,
    val phone: String
) {

    constructor(user: User) : this(
        user.id,
        user.name,
        user.surname,
        user.email,
        user.picture.thumbnail,
        user.phone
    )

    companion object {
        var DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem.id === newItem.id
            }

            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}

