package com.core.app.di.module

import com.core.features.detail.di.DetailModule
import com.core.features.home.di.HomeModule
import com.core.features.splash.di.SplashModule
import dagger.Module

@Module(
    includes = [
        SplashModule::class,
        HomeModule::class,
        DetailModule::class
    ]
)
abstract class FeaturesModule
