package com.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.core.common.android.Event

abstract class BaseViewModel : ViewModel() {

    protected val mThrowable: MutableLiveData<Event<Throwable>> = MutableLiveData()
    val throwable: LiveData<Event<Throwable>> = mThrowable

}