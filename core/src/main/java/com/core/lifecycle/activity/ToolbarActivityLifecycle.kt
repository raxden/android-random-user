package com.core.lifecycle.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import javax.inject.Inject

class ToolbarActivityLifecycle @Inject internal constructor(
        private val activity: AppCompatActivity
) : BaseActivityLifecycle(activity) {

    interface Callback {
        fun onCreateToolbarView(): Toolbar

        fun onToolbarViewCreated(toolbar: Toolbar)
    }

    private val callback: Callback
    private var mToolbar: Toolbar? = null

    init {
        // lifecycle-aware components, no need to unsubscribe/remove observers.
        callback = activity as Callback
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onCreate() {
        mToolbar = callback.onCreateToolbarView().also {
            activity.setSupportActionBar(it)
            activity.supportActionBar?.setDisplayShowTitleEnabled(false)
            it.setNavigationOnClickListener { activity.onBackPressed() }
            callback.onToolbarViewCreated(it)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        mToolbar = null
    }
}