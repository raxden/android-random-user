package com.core.common.test.rules

import timber.log.Timber

/**
 * Created by agomez on 10/03/2016.
 */
class TestReportingTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
        println(message)
    }
}
