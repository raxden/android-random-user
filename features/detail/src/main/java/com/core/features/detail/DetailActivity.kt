package com.core.features.detail

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.core.activity.BaseFragmentActivity
import com.core.common.android.extensions.getExtras
import com.core.common.android.extensions.loadParcelable
import com.core.common.android.extensions.loadFragment
import com.core.common.android.extensions.setupToolbar
import com.core.domain.User
import com.core.features.detail.databinding.DetailActivityBinding
import kotlinx.android.synthetic.main.detail_activity.*
import javax.inject.Inject

class DetailActivity : BaseFragmentActivity<DetailActivityBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.detail_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolbar(toolbar_view)
        loadFragment(content_view, savedInstanceState) { DetailFragment.newInstance(getExtras()) }
        loadParcelable<User>(User::class.java.name)?.let { viewModel.setUser(it) }
    }

    override fun onBindingCreated(binding: DetailActivityBinding) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }
}