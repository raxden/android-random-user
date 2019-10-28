package com.core.features.splash

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.core.activity.BaseFragmentActivity
import com.core.common.android.Status
import com.core.common.android.extensions.getExtras
import com.core.features.splash.databinding.SplashActivityBinding
import com.core.lifecycle.activity.InjectFragmentActivityLifecycle
import javax.inject.Inject

class SplashActivity : BaseFragmentActivity<SplashActivityBinding>(),
    InjectFragmentActivityLifecycle.Callback<SplashFragment> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.splash_activity

    override fun onBindingCreated(binding: SplashActivityBinding) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)

//        viewModel.user.observe(this, Observer {
//            when (it.status) {
//                Status.SUCCESS -> ""
//                Status.ERROR -> ""
//                Status.LOADING -> ""
//            }
//        })
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    // =============== InjectFragmentActivityLifecycle =============================================

    override fun onLoadFragmentContainer(): View = binding.contentView

    override fun onCreateFragment(): SplashFragment = SplashFragment.newInstance(getExtras())

    override fun onFragmentLoaded(fragment: SplashFragment) {}
}
