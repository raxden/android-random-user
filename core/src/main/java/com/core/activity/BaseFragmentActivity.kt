package com.core.activity

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseFragmentActivity<VDB : ViewDataBinding> : BaseActivity() {

    protected lateinit var binding: VDB

    override fun onCreateView(): View? {
        onCreateBinding()
        return binding.root
    }

    open fun onCreateBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        onBindingCreated(binding)
    }

    abstract fun onBindingCreated(binding: VDB)
}