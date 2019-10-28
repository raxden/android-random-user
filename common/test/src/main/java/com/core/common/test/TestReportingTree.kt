package com.core.common.test

import timber.log.Timber

/**
 * Created by agomez on 10/03/2016.
 */
class TestReportingTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        println("${Thread.currentThread().name} - $message")
    }
}
