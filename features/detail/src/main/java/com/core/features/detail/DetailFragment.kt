package com.core.features.detail

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.core.features.detail.databinding.DetailFragmentBinding
import com.core.fragment.BaseViewFragment
import javax.inject.Inject

class DetailFragment: BaseViewFragment<DetailFragmentBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(activity!!, viewModelFactory).get(DetailViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.detail_fragment

    override fun onBindingCreated(binding: DetailFragmentBinding) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }

    companion object {
        fun newInstance(bundle: Bundle?) = DetailFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}