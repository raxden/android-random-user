package com.core.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Abstract Activity for all Activities to extend.
 */
abstract class BaseActivity : AppCompatActivity(),
        HasSupportFragmentInjector {

    protected abstract val layoutId: Int

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    protected var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onCreateView()?.also { onViewCreated(it) }
    }

    open fun onCreateView(): View? = when {
        layoutId != 0 -> {
            setContentView(layoutId)
            findViewById(android.R.id.content)
        }
        else -> null
    }

    open fun onViewCreated(view: View) {
        rootView = view
    }

    // =============== HasFragmentInjector =========================================================

    override fun supportFragmentInjector() = fragmentInjector
}
