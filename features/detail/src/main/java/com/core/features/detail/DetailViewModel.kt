package com.core.features.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.core.BaseViewModel
import com.core.domain.User
import com.core.features.detail.model.UserDetailModel
import javax.inject.Inject

class DetailViewModel @Inject constructor(
) : BaseViewModel() {

    private val mUser = MutableLiveData<UserDetailModel>()
    val user: LiveData<UserDetailModel> = mUser

    fun setUser(user: User) {
        mUser.value = UserDetailModel(user)
    }
}