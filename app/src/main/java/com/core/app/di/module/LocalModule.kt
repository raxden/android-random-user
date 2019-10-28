package com.core.app.di.module

import android.content.Context
import androidx.room.Room
import com.core.app.BuildConfig
import com.core.data.local.AppDatabase
import com.core.data.local.dao.AccountDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object LocalModule {

    @JvmStatic
    @Provides
    @Singleton
    internal fun appDatabase(context: Context): AppDatabase = when {
        BuildConfig.FLAVOR == "mock" -> Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
        else -> Room.databaseBuilder(context, AppDatabase::class.java, "app_database.db")
    }
            .fallbackToDestructiveMigration()
            .build()

    @JvmStatic
    @Provides
    @Singleton
    internal fun accountDAO(database: AppDatabase): AccountDao = database.accountDao()
}
