package com.core.features.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.core.adapter.BaseListAdapter
import com.core.features.home.HomeViewModel
import com.core.features.home.R
import com.core.features.home.databinding.HomeUserListItemBinding
import com.core.features.home.model.UserModel

class HomeListAdapter(
    private val viewModel: HomeViewModel,
    diffCallback: DiffUtil.ItemCallback<UserModel>
) : BaseListAdapter<UserModel, HomeUserListItemBinding>(diffCallback) {

    override fun getItemViewType(position: Int): Int = R.layout.home_user_list_item

    override fun onBind(binding: HomeUserListItemBinding, item: UserModel) {
        binding.viewModel = viewModel
        binding.item = item
    }
}