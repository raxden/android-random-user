package com.core.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDB(
    @PrimaryKey
    var id: String
)