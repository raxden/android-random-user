package com.core.features.home

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.core.activity.BaseFragmentActivity
import com.core.common.android.extensions.getExtras
import com.core.features.home.databinding.HomeActivityBinding
import com.core.lifecycle.activity.InjectFragmentActivityLifecycle
import javax.inject.Inject

class HomeActivity : BaseFragmentActivity<HomeActivityBinding>(),
    InjectFragmentActivityLifecycle.Callback<HomeFragment> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.home_activity

    override fun onBindingCreated(binding: HomeActivityBinding) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    // =============== InjectFragmentActivityLifecycle =============================================

    override fun onLoadFragmentContainer(): View = binding.contentView

    override fun onCreateFragment(): HomeFragment = HomeFragment.newInstance(getExtras())

    override fun onFragmentLoaded(fragment: HomeFragment) {}
}