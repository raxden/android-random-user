package com.core.common.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.core.common.test.rules.RxSchedulerRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import timber.log.Timber

abstract class BaseTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @Before
    open fun setUp() {
        Timber.plant(TestReportingTree())
    }

    @After
    open fun tearDown() {
    }
}