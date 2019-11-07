package com.core.features.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.core.activity.BaseFragmentActivity
import com.core.common.android.extensions.getExtras
import com.core.common.android.extensions.loadFragment
import com.core.common.android.extensions.setupToolbar
import com.core.features.home.databinding.HomeActivityBinding
import com.core.features.home.view.FilterBottomSheetDialog
import kotlinx.android.synthetic.main.home_activity.*
import javax.inject.Inject

class HomeActivity : BaseFragmentActivity<HomeActivityBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var filterDialog: FilterBottomSheetDialog? = null

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.home_activity

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(
            FilterBottomSheetDialog::class.java.simpleName,
            filterDialog?.isShowing == true
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolbar(toolbar_view)
        loadFragment(content_view, savedInstanceState) { HomeFragment.newInstance(getExtras()) }

        savedInstanceState?.getBoolean(FilterBottomSheetDialog::class.java.simpleName)?.let {
            if (it) showFilterView()
        }
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

    override fun onDestroy() {
        filterDialog?.dismiss()
        filterDialog = null

        super.onDestroy()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    // =============================================================================================

    private fun showFilterView() {
        filterDialog = FilterBottomSheetDialog(this, viewModel)
        filterDialog?.show()
    }
}