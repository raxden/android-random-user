package com.core.features.splash

import android.app.Activity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.core.ViewModelKey
import com.core.activity.BaseActivityModule
import com.core.lifecycle.activity.InjectFragmentActivityLifecycle
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet

@Module
abstract class SplashModule {

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    internal abstract fun activity(): SplashActivity

    @Module(includes = [BaseActivityModule::class])
    abstract class SplashActivityModule {

        @Binds
        internal abstract fun activity(activity: SplashActivity): Activity

        @Binds
        @IntoSet
        internal abstract fun injectFragment(lifecycleObserver: InjectFragmentActivityLifecycle<SplashFragment>): LifecycleObserver

        @Binds
        @IntoMap
        @ViewModelKey(SplashViewModel::class)
        internal abstract fun splashViewModel(viewModel: SplashViewModel): ViewModel

        @ContributesAndroidInjector
        internal abstract fun fragment(): SplashFragment
    }
}