package com.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.core.data.local.dao.UserDao
import com.core.data.local.model.UserDB

@Database(
        entities = [
            UserDB::class
        ],
        version = 1,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}