package com.core.common.test

import org.mockito.Mockito

object MockitoUtils {

    fun <T> anyObject(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T
}