package com.core.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.core.BaseViewModel
import com.core.common.android.Event
import com.core.features.home.model.UserModel
import com.core.features.home.usecase.GetUsersUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel() {

    private val mUsers = MutableLiveData<MutableList<UserModel>>()
    val users: LiveData<MutableList<UserModel>> = mUsers

    init {
        loadUsers()
    }

    fun onItemSelected(position: Int) {

    }

    private fun loadUsers() {
        getUsersUseCase.execute(0, 100)
            .map { list -> list.map { UserModel(it) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    mUsers.value = it.toMutableList()
                },
                onComplete = {
                    mUsers.value = mutableListOf()
                },
                onError = {
                    mThrowable.value = Event(it)
                }
            )
            .addTo(mCompositeDisposable)
    }
}