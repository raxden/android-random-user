package com.core.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.core.BaseViewModel
import com.core.common.android.Event
import com.core.common.android.Pager
import com.core.domain.User
import com.core.features.home.model.UserModel
import com.core.features.home.usecase.GetUsersPagedUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import com.core.common.android.extensions.notifyObservers

class HomeViewModel @Inject constructor(
    private val getUsersPagedUseCase: GetUsersPagedUseCase
) : BaseViewModel() {

    private val mUsers = MutableLiveData<MutableList<UserModel>>()
    val users: LiveData<MutableList<UserModel>> = mUsers

    private val mPager = Pager<User>(1, 20)

    init {
        mUsers.value = mutableListOf()
        loadUsers(mPager)
    }

    override fun onCleared() {
        mPager.clear()
        super.onCleared()
    }

    fun refreshContent() {
        loadUsers(mPager.restart())
    }

    fun loadMoreUsers() {
        if (mPager.isPageRequested() || !mPager.hasMoreResults()) return
        loadUsers(mPager.next())
    }

    fun onItemSelected(position: Int) {

    }

    private fun loadUsers(pager: Pager<User>) {
        getUsersPagedUseCase.execute(pager)
            .map { it.getPageData().map { item -> UserModel(item) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { data ->
                    mUsers.value?.addAll(data)
                    mUsers.notifyObservers()
                },
                onError = {
                    mThrowable.value = Event(it)
                }
            )
            .addTo(mCompositeDisposable)
    }
}