package com.core.features.home.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.core.component.BaseComponentBindingView
import com.core.features.home.R
import com.core.features.home.databinding.UserListItemViewBinding
import com.core.features.home.model.UserModel

class UserListItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : BaseComponentBindingView<UserListItemViewBinding>(context, attrs, defStyleAttr, defStyleRes) {

    override val mStyleable: IntArray
        get() = R.styleable.UserListItemView

    override val mLayoutId: Int
        get() = R.layout.user_list_item_view

    override fun onLoadStyledAttributes(attrs: TypedArray) {}

    override fun onBindingCreated(binding: UserListItemViewBinding) {}

    fun setItem(item: UserModel) {
        mBinding.item = item
        mBinding.executePendingBindings()
    }
}