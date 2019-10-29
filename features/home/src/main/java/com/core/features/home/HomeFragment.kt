package com.core.features.home

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.core.features.home.databinding.HomeFragmentBinding
import com.core.fragment.BaseViewFragment
import javax.inject.Inject

class HomeFragment: BaseViewFragment<HomeFragmentBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(activity!!, viewModelFactory).get(HomeViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.home_fragment

    override fun onBindingCreated(binding: HomeFragmentBinding) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }

    companion object {
        fun newInstance(bundle: Bundle?) = HomeFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}