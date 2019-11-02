package com.core.features.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.core.activity.BaseFragmentActivity
import com.core.common.android.extensions.getExtras
import com.core.features.home.databinding.HomeActivityBinding
import com.core.features.home.view.FilterBottomSheetDialog
import com.core.features.home.view.FilterView
import com.core.lifecycle.activity.InjectFragmentActivityLifecycle
import com.core.lifecycle.activity.ToolbarActivityLifecycle
import com.core.navigation.NavigationHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import javax.inject.Inject

class HomeActivity : BaseFragmentActivity<HomeActivityBinding>(),
    ToolbarActivityLifecycle.Callback,
    InjectFragmentActivityLifecycle.Callback<HomeFragment> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var navigationHelper: NavigationHelper

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.home_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.userSelected.observe(this, Observer {
            it.getContentIfNotHandled()?.let { user ->
                navigationHelper.launchDetail(user)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> showFilterView()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBindingCreated(binding: HomeActivityBinding) {
        binding.setVariable(BR.viewModel, viewModel)
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

    override fun onCreateFragment(): HomeFragment = HomeFragment.newInstance(getExtras())

    override fun onFragmentLoaded(fragment: HomeFragment) {}

    // =============================================================================================

    private fun showFilterView() {
        FilterBottomSheetDialog(this, viewModel).show()
    }
}