package com.core.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.core.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Provides base activity dependencies. This must be included in all activity modules, which must
 * provide a concrete implementation of [Activity].
 */
@Module
abstract class BaseActivityModule {

    @Binds
    abstract fun viewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Module
    companion object {

        @JvmStatic
        @Provides
        internal fun activity(activity: Activity): AppCompatActivity = activity as AppCompatActivity
    }
}
