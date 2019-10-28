package com.core.features.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.core.common.android.extensions.startFadeInAnimation
import com.core.features.splash.databinding.SplashFragmentBinding
import com.core.fragment.BaseViewFragment
import javax.inject.Inject

class SplashFragment: BaseViewFragment<SplashFragmentBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: SplashViewModel by lazy {
        ViewModelProvider(activity!!, viewModelFactory).get(SplashViewModel::class.java)
    }

    override val layoutId: Int
        get() = R.layout.splash_fragment

    override fun onBindingCreated(binding: SplashFragmentBinding) {
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoImageView.apply {
            postDelayed({
                visibility = View.VISIBLE
                startFadeInAnimation()
            }, 1000)
        }
    }

    companion object {
        fun newInstance(bundle: Bundle?) = SplashFragment().apply {
            arguments = bundle ?: Bundle()
        }
    }
}