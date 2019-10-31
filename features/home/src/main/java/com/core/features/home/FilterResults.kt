package com.core.features.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.core.common.android.extensions.notifyObservers
import com.core.features.home.model.UserModel

class FilterResults {

    val name: MutableLiveData<String> = MutableLiveData()
    val surname: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()

    private val mUsers = MutableLiveData<MutableList<UserModel>>()
    val users = MediatorLiveData<MutableList<UserModel>>().apply {

        var users: List<UserModel>? = null
        var queryName: String? = null
        var querySurname: String? = null
        var queryEmail: String? = null

        fun filterData() {
            value = users?.filter {
                it.name.startsWith(queryName ?: "")
                        && it.surname.startsWith(querySurname ?: "")
                        && it.email.startsWith(queryEmail ?: "")
            }?.toMutableList()
        }

        addSource(mUsers) {
            users = it
            filterData()
        }
        addSource(name) {
            queryName = it
            filterData()
        }
        addSource(surname) {
            querySurname = it
            filterData()
        }
        addSource(email) {
            queryEmail = it
            filterData()
        }
    }

    init {
        mUsers.value = mutableListOf()
    }

    fun setData(data: List<UserModel>) {
        mUsers.value = data.toMutableList()
    }

    fun removeData(data: UserModel) {
        mUsers.value?.remove(data)
        mUsers.notifyObservers()
    }

    fun clearData() {
        mUsers.value?.clear()
        mUsers.notifyObservers()
    }

    fun addData(data: List<UserModel>) {
        mUsers.value?.addAll(data)
        mUsers.notifyObservers()
    }
}