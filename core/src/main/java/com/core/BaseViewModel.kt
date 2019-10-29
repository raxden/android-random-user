package com.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.core.common.android.Event
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val mThrowable: MutableLiveData<Event<Throwable>> = MutableLiveData()
    val throwable: LiveData<Event<Throwable>> = mThrowable

    protected val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        mCompositeDisposable.clear()
        super.onCleared()
    }
}