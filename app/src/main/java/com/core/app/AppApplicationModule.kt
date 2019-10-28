package com.core.app

import android.content.Context
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Provides application-wide dependencies.
 */
@Module
abstract class AppApplicationModule {

    @Binds
    @Singleton
    internal abstract fun context(application: AppApplication): Context
}
