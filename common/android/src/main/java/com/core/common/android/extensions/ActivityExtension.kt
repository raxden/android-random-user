package com.core.common.android.extensions

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun <T : Parcelable> Activity.loadParcelable(key: String): T? = intent?.extras?.getParcelable(key)

fun Activity.getExtras(): Bundle? = intent?.extras

fun AppCompatActivity.setupToolbar(
    toolbar: Toolbar,
    titleEnabled: Boolean = false
): Toolbar = toolbar.also {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayShowTitleEnabled(titleEnabled)
    toolbar.setNavigationOnClickListener { onBackPressed() }
}


@Suppress("UNCHECKED_CAST")
fun <TFragment : Fragment> FragmentActivity.loadFragment(
    containerView: View,
    savedInstanceState: Bundle?,
    create: () -> TFragment
): TFragment? = savedInstanceState?.let {
    supportFragmentManager.findFragmentById(containerView.id) as? TFragment
} ?: create().also { fragment ->
    supportFragmentManager.beginTransaction()
        .replace(containerView.id, fragment, fragment.javaClass.simpleName)
        .commit()
}

