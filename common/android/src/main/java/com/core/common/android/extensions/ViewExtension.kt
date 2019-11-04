package com.core.common.android.extensions

import android.os.SystemClock
import android.view.View
import android.view.View.*

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    setOnClickListener(SafeClickListener { onSafeClick(it) })
}

class SafeClickListener(
        private var defaultInterval: Int = 500,
        private val onSafeCLick: (View) -> Unit
) : OnClickListener {

    private var lastTimeClicked: Long = 0

    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) return
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}