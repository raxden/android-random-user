package com.core.features.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.core.BaseViewModel
import com.core.common.android.Resource
import com.core.domain.User
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val forwardUseCase: ForwardUseCase
) : BaseViewModel() {

    private val mVersion = MutableLiveData<String>()
    val version: LiveData<String> = mVersion

//    val user: LiveData<Resource<User>> = forwardUseCase.execute()
}