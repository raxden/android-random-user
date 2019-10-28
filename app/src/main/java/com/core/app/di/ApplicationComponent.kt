package com.core.app.di

import com.core.app.AppApplication
import com.core.app.AppApplicationModule
import com.core.app.di.module.FeaturesModule
import com.core.app.di.module.LocalModule
import com.core.app.di.module.RemoteModule
import com.core.app.di.module.RepositoryModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppApplicationModule::class,
        LocalModule::class,
        RemoteModule::class,
        RepositoryModule::class,
        FeaturesModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<AppApplication> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<AppApplication>
}
