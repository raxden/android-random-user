package com.core.data.repository

import com.core.data.BaseRepositoryTest
import com.core.data.remote.entity.LoginEntity
import com.core.data.remote.entity.UserEntity
import com.core.data.repository.mapper.UserEntityDataMapper
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

class UserRepositoryTest : BaseRepositoryTest() {

    var userEntityList = listOf(
        UserEntity(login = LoginEntity("1")),
        UserEntity(login = LoginEntity("2")),
        UserEntity(login = LoginEntity("3")),
        UserEntity(login = LoginEntity("5")),
        UserEntity(login = LoginEntity("5"))
    )

    lateinit var userRepository: UserRepositoryImpl

    @Before
    override fun setUp() {
        super.setUp()

        userRepository = UserRepositoryImpl(gateway, UserEntityDataMapper())
    }

    @Test
    fun `Get users from server and remove duplicates`() {
        Mockito.`when`(gateway.users(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
            .thenReturn(Single.just(userEntityList))

        userRepository.list(1, 100)
            .test()
            .assertNoErrors()
            .assertValue { it.size() == 4 }
    }
}