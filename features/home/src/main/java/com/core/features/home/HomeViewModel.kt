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
import com.core.features.home.usecase.ExcludeUserUseCase

class HomeViewModel @Inject constructor(
    private val getUsersPagedUseCase: GetUsersPagedUseCase,
    private val excludeUserUseCase: ExcludeUserUseCase
) : BaseViewModel() {

    private val mFilterResults = FilterResults()

    private val mLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = mLoading

    private val mPager = Pager<User>(1, 20)

    init {
        mLoading.value = true
        loadUsers(mPager)
    }

    override fun onCleared() {
        mPager.clear()
        super.onCleared()
    }

    fun getResults() = mFilterResults

    fun refreshContent() {
        mFilterResults.clearData()
        mLoading.value = true
        loadUsers(mPager.clear())
    }

    fun loadMoreUsers() {
        if (mPager.isPageRequested() || !mPager.hasMoreResults()) return
        loadUsers(mPager.next())
    }

    fun onItemSelected(item: UserModel) {

    }

    fun onItemRemoved(item: UserModel) {
        excludeUserUseCase.execute(item.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    mFilterResults.removeData(item)
                },
                onError = {
                    mThrowable.value = Event(it)
                }
            )
            .addTo(mCompositeDisposable)
    }

    private fun loadUsers(pager: Pager<User>) {
        getUsersPagedUseCase.execute(pager)
            .map { it.getPageData().map { item -> UserModel(item) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { data ->
                    mFilterResults.addData(data)
                    mLoading.value = false
                },
                onError = {
                    mThrowable.value = Event(it)
                    mLoading.value = false
                }
            )
            .addTo(mCompositeDisposable)
    }
}