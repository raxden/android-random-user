package com.core.data

import com.core.common.test.BaseTest
import com.core.data.remote.AppGateway
import org.mockito.Mock

abstract class BaseRepositoryTest: BaseTest() {

    @Mock
    lateinit var gateway: AppGateway
}
