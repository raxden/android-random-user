package com.core.features.detail

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.core.activity.BaseFragmentActivity
import com.core.common.android.extensions.getExtras
import com.core.features.detail.databinding.DetailActivityBinding
import com.core.lifecycle.activity.InjectFragmentActivityLifecycle
import javax.inject.Inject

class DetailActivity : BaseFragmentActivity<DetailActivityBinding>(),
    InjectFragmentActivityLifecycle.Callback<DetailFragment> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.detail_activity

    override fun onBindingCreated(binding: DetailActivityBinding) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    // =============== InjectFragmentActivityLifecycle =============================================

    override fun onLoadFragmentContainer(): View = binding.contentView

    override fun onCreateFragment(): DetailFragment = DetailFragment.newInstance(getExtras())

    override fun onFragmentLoaded(fragment: DetailFragment) {}
}