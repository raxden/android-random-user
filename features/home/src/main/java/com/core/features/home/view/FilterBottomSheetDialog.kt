package com.core.features.home.view

import android.content.Context
import android.view.View
import com.core.features.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class FilterBottomSheetDialog(context: Context, viewModel: HomeViewModel) : BottomSheetDialog(context) {

    private val filterView = FilterView(context)

    init {
        filterView.setViewModel(viewModel)
        filterView.setOnCloseClickListener(View.OnClickListener { dismiss() })
        setContentView(filterView)
    }
}