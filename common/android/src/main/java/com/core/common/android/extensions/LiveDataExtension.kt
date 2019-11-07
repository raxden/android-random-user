package com.core.common.android.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.core.common.android.Event

fun <T> MutableLiveData<T>.notifyObservers() {
    this.value = this.value
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(
    liveData: L,
    body: (T) -> Unit
) {
    liveData.observe(this, Observer(body))
}

fun <T : Any, L : LiveData<T>> Fragment.observe(
    liveData: L,
    body: (T) -> Unit
) {
    liveData.observe(viewLifecycleOwner, Observer(body))
}

inline fun <T : Any, L : LiveData<Event<T>>> LifecycleOwner.observeEvent(
    liveData: L,
    crossinline body: (T) -> Unit
) {
    liveData.observe(this, Observer {
        it?.getContentIfNotHandled()?.let(body)
    })
}

inline fun <T : Any, L : LiveData<Event<T>>> Fragment.observeEvent(
    liveData: L,
    crossinline body: (T) -> Unit
) {
    liveData.observe(viewLifecycleOwner, Observer {
        it?.getContentIfNotHandled()?.let(body)
    })
}