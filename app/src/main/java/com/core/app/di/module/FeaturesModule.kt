package com.core.app.di.module

import com.core.features.detail.di.DetailModule
import com.core.features.home.di.HomeModule
import dagger.Module

@Module(
    includes = [
        HomeModule::class,
        DetailModule::class
    ]
)
abstract class FeaturesModule
