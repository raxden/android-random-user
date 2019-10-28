package com.core.app.di.module

import com.core.features.splash.SplashModule
import dagger.Module

@Module(
    includes = [
        SplashModule::class
    ]
)
abstract class FeaturesModule
