package com.core.features.detail.di

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.core.common.dagger.ViewModelKey
import com.core.activity.BaseActivityModule
import com.core.features.detail.DetailActivity
import com.core.features.detail.DetailFragment
import com.core.features.detail.DetailViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class DetailModule {

    @ContributesAndroidInjector(modules = [DetailActivityModule::class])
    internal abstract fun activity(): DetailActivity

    @Module(includes = [BaseActivityModule::class])
    abstract class DetailActivityModule {

        @Binds
        internal abstract fun activity(activity: DetailActivity): Activity

        @Binds
        @IntoMap
        @ViewModelKey(DetailViewModel::class)
        internal abstract fun detailViewModel(viewModel: DetailViewModel): ViewModel

        @ContributesAndroidInjector
        internal abstract fun fragment(): DetailFragment
    }
}