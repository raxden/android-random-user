package com.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.core.data.local.dao.ExcludedUserDao
import com.core.data.local.model.ExcludedUserDB

@Database(
        entities = [
            ExcludedUserDB::class
        ],
        version = 1,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun excludedUserDao(): ExcludedUserDao
}