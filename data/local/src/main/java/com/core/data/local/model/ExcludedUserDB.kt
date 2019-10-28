package com.core.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "excluded_users")
data class ExcludedUserDB(
    @PrimaryKey
    var id: String
)