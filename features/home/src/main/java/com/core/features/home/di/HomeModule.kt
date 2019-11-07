package com.core.features.home.di

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.core.common.dagger.ViewModelKey
import com.core.activity.BaseActivityModule
import com.core.features.home.HomeActivity
import com.core.features.home.HomeFragment
import com.core.features.home.HomeViewModel
import com.core.navigation.NavigationModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {

    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    internal abstract fun activity(): HomeActivity

    @Module(includes = [BaseActivityModule::class, NavigationModule::class])
    abstract class HomeActivityModule {

        @Binds
        internal abstract fun activity(activity: HomeActivity): Activity

        @Binds
        @IntoMap
        @ViewModelKey(HomeViewModel::class)
        internal abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel

        @ContributesAndroidInjector
        internal abstract fun fragment(): HomeFragment
    }
}