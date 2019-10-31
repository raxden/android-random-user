package com.core.app.di.module

import android.content.Context
import androidx.room.Room
import com.core.data.local.AppDatabase
import com.core.data.local.dao.ExcludedUserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object LocalModule {

    @JvmStatic
    @Provides
    @Singleton
    internal fun appDatabase(
        context: Context
    ): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "app_database.db")
        .fallbackToDestructiveMigration()
        .build()

    @JvmStatic
    @Provides
    @Singleton
    internal fun excludeUserDao(
        database: AppDatabase
    ): ExcludedUserDao = database.excludedUserDao()
}
