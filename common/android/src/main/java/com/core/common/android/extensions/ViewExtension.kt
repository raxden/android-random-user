package com.core.common.android.extensions

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat

fun View.isVisible() = visibility == VISIBLE

fun View.visible() {
    visibility = VISIBLE
}

fun View.gone() {
    visibility = GONE
}

fun View.isGone() = visibility == GONE

fun View.invisible() {
    visibility = INVISIBLE
}

fun View.isInvisible() = visibility == INVISIBLE

fun View.getColor(resId: Int) = ContextCompat.getColor(context, resId)

fun View.getDrawable(resId: Int) = ContextCompat.getDrawable(context, resId)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View = LayoutInflater.from(context)
        .inflate(layoutRes, this, false)

fun View.startFadeInAnimation() {
    startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in))
}

fun View.startFadeOutAnimation() {
    startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out))
}

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