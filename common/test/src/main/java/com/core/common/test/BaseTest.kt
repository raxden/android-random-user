package com.core.common.test

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.core.common.test.rules.RxSchedulerRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import timber.log.Timber

@RunWith(AndroidJUnit4::class)
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

    fun getContext(): Context = InstrumentationRegistry.getInstrumentation().context
}