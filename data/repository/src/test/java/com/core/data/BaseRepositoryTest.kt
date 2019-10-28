package com.core.data

import com.core.common.test.BaseTest
import com.core.common.test.rules.CoroutinesMainDispatcherRule
import com.core.data.remote.AppGateway
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

abstract class BaseRepositoryTest: BaseTest() {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesMainDispatcherRule()

    @MockK
    protected lateinit var gateway: AppGateway

    @Before
    override fun setUp() {
        super.setUp()
        MockKAnnotations.init(this)
    }
}