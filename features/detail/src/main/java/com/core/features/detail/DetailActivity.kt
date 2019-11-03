package com.core.features.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.core.activity.BaseFragmentActivity
import com.core.common.android.extensions.getExtras
import com.core.domain.User
import com.core.features.detail.databinding.DetailActivityBinding
import com.core.lifecycle.activity.InjectFragmentActivityLifecycle
import com.core.lifecycle.activity.ToolbarActivityLifecycle
import javax.inject.Inject

class DetailActivity : BaseFragmentActivity<DetailActivityBinding>(),
    ToolbarActivityLifecycle.Callback,
    InjectFragmentActivityLifecycle.Callback<DetailFragment> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.detail_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getExtras()?.getParcelable<User>(User::class.java.name)?.let {
            viewModel.setUser(it)
        }
    }

    override fun onBindingCreated(binding: DetailActivityBinding) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    // =============== ToolbarActivityLifecycle ====================================================

    override fun onCreateToolbarView(): Toolbar = binding.toolbarView

    override fun onToolbarViewCreated(toolbar: Toolbar) {}

    // =============== InjectFragmentActivityLifecycle =============================================

    override fun onLoadFragmentContainer(): View = binding.contentView

    override fun onCreateFragment(): DetailFragment = DetailFragment.newInstance(getExtras())

    override fun onFragmentLoaded(fragment: DetailFragment) {}
}