package com.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter<T : Any, VDB : ViewDataBinding>(
        diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseListAdapter<T, VDB>.ViewDataBindingHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewDataBindingHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<VDB>(layoutInflater, viewType, parent, false)
        return ViewDataBindingHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewDataBindingHolder, position: Int) {
        getItem(position)?.run { holder.bind(this) }
    }

    abstract override fun getItemViewType(position: Int): Int

    abstract fun onBind(binding: VDB, item: T)

    inner class ViewDataBindingHolder(
        private val binding: VDB
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T) {
            onBind(binding, item)
            binding.executePendingBindings()
        }
    }
}