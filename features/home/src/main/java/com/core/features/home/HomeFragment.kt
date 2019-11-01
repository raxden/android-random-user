package com.core.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.core.common.android.Status
import com.core.common.android.extensions.OnPageEndlessListener
import com.core.common.android.extensions.setOnPageEndlessListener
import com.core.features.home.adapter.HomeListAdapter
import com.core.features.home.databinding.HomeFragmentBinding
import com.core.features.home.model.UserModel
import com.core.fragment.BaseViewFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import timber.log.Timber
import javax.inject.Inject

class HomeFragment : BaseViewFragment<HomeFragmentBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(activity!!, viewModelFactory).get(HomeViewModel::class.java)
    }

    private lateinit var homeListAdapter: HomeListAdapter

    override val layoutId: Int
        get() = R.layout.home_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeListAdapter = HomeListAdapter(viewModel, UserModel.DIFF_CALLBACK)
    }

    override fun onBindingCreated(binding: HomeFragmentBinding) {
        binding.setVariable(BR.viewModel, viewModel)

        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.refreshContent() }
        binding.recyclerView.apply {
            adapter = homeListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setOnPageEndlessListener(object : OnPageEndlessListener {
                override fun onPageEndless() {
                    viewModel.loadMoreUsers()
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.throwable.observe(viewLifecycleOwner, Observer {
            MaterialAlertDialogBuilder(activity)
                .setTitle(R.string.home_error_unknown_title)
                .setMessage(R.string.home_error_unknown_message)
                .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        })
        viewModel.users.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.ERROR -> Toast
                    .makeText(context, R.string.home_error_to_retrieve_users, Toast.LENGTH_LONG)
                    .show()
                else -> homeListAdapter.submitList(it.data?.toList())
            }
        })
    }

    companion object {
        fun newInstance(bundle: Bundle?) = HomeFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}