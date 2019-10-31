package com.core.features.home.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.core.component.BaseComponentBindingView
import com.core.features.home.HomeViewModel
import com.core.features.home.R
import com.core.features.home.databinding.FilterViewBinding


class FilterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : BaseComponentBindingView<FilterViewBinding>(context, attrs, defStyleAttr, defStyleRes) {

    override val mStyleable: IntArray
        get() = R.styleable.FilterView

    override val mLayoutId: Int
        get() = R.layout.filter_view

    override fun onLoadStyledAttributes(attrs: TypedArray) {}

    override fun onBindingCreated(binding: FilterViewBinding) {}

    fun setOnCloseClickListener(listener: OnClickListener) {
        mBinding.closeView.setOnClickListener { listener.onClick(it) }
    }

    fun setViewModel(viewModel: HomeViewModel) {
        mBinding.viewModel = viewModel
        mBinding.executePendingBindings()
    }
}