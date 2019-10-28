package com.core.lifecycle.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.core.lifecycle.BaseLifecycleObserver

abstract class BaseActivityLifecycle(
    activity: AppCompatActivity
) : BaseLifecycleObserver(activity.lifecycle) {

    protected var savedInstanceState: Bundle? = null

    fun onCreate(savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState
    }

    open fun onSaveInstanceState(outState: Bundle) {}
}