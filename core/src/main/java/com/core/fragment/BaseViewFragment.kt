package com.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseViewFragment<VDB : ViewDataBinding>
    : BaseFragment() {

    protected abstract val layoutId: Int
    lateinit var binding: VDB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<VDB>(inflater, layoutId, container, false).let { binding ->
            this.binding = binding
            binding.lifecycleOwner = viewLifecycleOwner    // Layout requirement to listen any changes on LiveData values
            onBindingCreated(binding)
            binding.root
        }
    }

    abstract fun onBindingCreated(binding: VDB)
}