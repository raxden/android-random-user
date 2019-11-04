package com.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val mThrowable: MutableLiveData<Throwable> = MutableLiveData()
    val throwable: LiveData<Throwable> = mThrowable

    protected val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        mCompositeDisposable.clear()
        super.onCleared()
    }
}