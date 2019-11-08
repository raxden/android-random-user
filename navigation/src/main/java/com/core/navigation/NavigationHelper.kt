package com.core.navigation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.core.domain.User
import javax.inject.Inject

class NavigationHelper @Inject internal constructor(
    private val activity: Activity
) {

    fun launchDetail(user: User) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("app://randomuser/detail")).apply {
            putExtra(User::class.java.name, user)
            setPackage(activity.packageName)
        }
        activity.startActivity(intent)
    }
}