package com.core.common.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.core.common.test.rules.RxSchedulerRule
import io.mockk.MockKAnnotations
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import timber.log.Timber

abstract class BaseTest {

//    @get:Rule
//    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @Before
    open fun setUp() {
        MockKAnnotations.init(this)
        Timber.plant(TestReportingTree())
    }

    @After
    open fun tearDown() {
    }
}