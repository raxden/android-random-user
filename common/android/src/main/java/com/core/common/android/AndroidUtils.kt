/*
 * Copyright 2014 Ángel Gómez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.core.common.android

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import timber.log.Timber

object AndroidUtils {

    fun getPackageName(context: Context): String {
        var packageName = ""
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageName = pInfo.packageName
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.e(e)
        }
        return packageName
    }

    fun getVersionName(context: Context): String {
        var versionName = ""
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            versionName = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.e(e)
        }
        return versionName
    }

    @Suppress("DEPRECATION")
    fun getVersionCode(context: Context): Long {
        return try {
            context.packageManager.getPackageInfo(context.packageName, 0).let {
                if (hasPie()) it.longVersionCode else it.versionCode.toLong()
            }
        } catch (e: PackageManager.NameNotFoundException) {
            0L
        }
    }

    fun closeKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.findViewById<View>(android.R.id.content).windowToken, 0)
    }

    fun openKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    val isEmulator: Boolean
        get() = (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || "google_sdk" == Build.PRODUCT)

    fun hasVirtualNavigationBar(context: Context): Boolean {
        val id = context.resources.getIdentifier("config_showNavigationBar", "bool", "android")
        return id > 0 && context.resources.getBoolean(id)
    }

    /**
     * Checks if the device has Lolllipop or higher version.
     * @return `true` if device is a tablet.
     */
    fun hasLollipop(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    /**
     * Checks if the device has Marshmallow or higher version.
     * @return `true` if device is a tablet.
     */
    fun hasMarshmallow(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    /**
     * Checks if the device has Marshmallow or higher version.
     * @return `true` if device is a tablet.
     */
    fun hasNougat(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    fun hasPie(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
}
