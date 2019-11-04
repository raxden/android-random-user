package com.core.common.android.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable

fun <T: Parcelable> Activity.getParcelable(key: String): T? = intent?.extras?.getParcelable(key)

fun Activity.getExtras(): Bundle? = intent?.extras