package com.core.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.core.BaseViewModel
import com.core.common.android.Event
import com.core.common.android.Pager
import com.core.common.android.Resource
import com.core.common.android.Status
import com.core.common.android.extensions.notifyObservers
import com.core.domain.User
import com.core.features.home.model.UserModel
import com.core.features.home.usecase.GetUsersPagedUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import com.core.features.home.usecase.ExcludeUserUseCase
import java.util.*

class HomeViewModel @Inject constructor(
    private val getUsersPagedUseCase: GetUsersPagedUseCase,
    private val excludeUserUseCase: ExcludeUserUseCase
) : BaseViewModel() {

    private val mUserSelected = MutableLiveData<Event<User>>()
    val userSelected: LiveData<Event<User>> = mUserSelected

    val name: MutableLiveData<String> = MutableLiveData()
    val surname: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()

    private val mUsers = MutableLiveData<Resource<MutableList<UserModel>>>()
    val users = MediatorLiveData<Resource<List<UserModel>>>().apply {

        var users: Resource<MutableList<UserModel>>? = null
        var queryName: String? = null
        var querySurname: String? = null
        var queryEmail: String? = null

        fun filterData() {
            value = when (users?.status) {
                Status.SUCCESS -> Resource.success(
                    users?.data?.filter {
                        val locale = Locale.getDefault()
                        it.name.toLowerCase(locale).startsWith(queryName ?: "")
                                && it.surname.toLowerCase(locale).startsWith(querySurname ?: "")
                                && it.email.toLowerCase(locale).startsWith(queryEmail ?: "")
                    }
                )
                else -> users
            }
        }

        addSource(mUsers) {
            users = it
            filterData()
        }
        addSource(name) {
            queryName = it.toLowerCase(Locale.getDefault())
            filterData()
        }
        addSource(surname) {
            querySurname = it.toLowerCase(Locale.getDefault())
            filterData()
        }
        addSource(email) {
            queryEmail = it.toLowerCase(Locale.getDefault())
            filterData()
        }
    }

    private val mPager = Pager<User>(1, 20)

    init {
        mUsers.value = Resource.loading(mutableListOf())
        loadUsers(mPager)
    }

    fun refreshContent() {
        mUsers.value = Resource.loading(mutableListOf())
        loadUsers(mPager.clear())
    }

    fun loadMoreUsers() {
        if (mPager.isPageRequested() || !mPager.hasMoreResults()) return
        loadUsers(mPager.next())
    }

    fun onItemSelected(item: UserModel) {
        mPager.getAllData().find { it.id == item.id }?.let { user ->
            mUserSelected.value = Event(user)
        }
    }

    fun onItemRemoved(item: UserModel) {
        excludeUserUseCase.execute(item.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    val itemToRemove = mUsers.value?.data?.find { it.id == item.id }
                    mUsers.value?.data?.remove(itemToRemove)
                    mUsers.notifyObservers()
                },
                onError = {
                    mThrowable.value = it
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
                    val currentData = mUsers.value?.data
                    currentData?.addAll(data)
                    mUsers.value = Resource.success(currentData)
                },
                onError = {
                    val currentData = mUsers.value?.data
                    mUsers.value = Resource.error(it, currentData)
                }
            )
            .addTo(mCompositeDisposable)
    }
}