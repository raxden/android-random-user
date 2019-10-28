package com.core.app

import com.core.BaseApplication
import com.core.app.di.DaggerApplicationComponent

class AppApplication: BaseApplication() {

    override fun initDaggerApplicationComponent() {
        DaggerApplicationComponent.factory().create(this).inject(this)
    }
}