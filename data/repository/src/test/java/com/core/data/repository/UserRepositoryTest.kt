package com.core.data.repository

import com.core.common.test.BaseTest
import com.core.data.local.dao.ExcludedUserDao
import com.core.data.local.model.ExcludedUserDB
import com.core.data.remote.AppGateway
import com.core.data.remote.entity.LoginEntity
import com.core.data.remote.entity.UserEntity
import com.core.data.repository.mapper.UserEntityDataMapper
import com.core.domain.User
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class UserRepositoryTest : BaseTest() {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var gateway: AppGateway
    @Mock
    lateinit var excludedUserDao: ExcludedUserDao

    var userEntityList = listOf(
        UserEntity(login = LoginEntity("1")),
        UserEntity(login = LoginEntity("2")),
        UserEntity(login = LoginEntity("3")),
        UserEntity(login = LoginEntity("5")),
        UserEntity(login = LoginEntity("5"))
    )

    var excludedUserList = listOf(
        ExcludedUserDB("1"),
        ExcludedUserDB("2")
    )

    var result = listOf(
        User("3"),
        User("5")
    )

    lateinit var userRepository: UserRepositoryImpl

    @Before
    override fun setUp() {
        super.setUp()

        userRepository = UserRepositoryImpl(gateway, excludedUserDao, UserEntityDataMapper())
    }

    @Test
    fun `Get users from server and check that duplicates and excluded users were removed`() {
        `when`(gateway.users(anyInt(), anyInt())).thenReturn(Single.just(userEntityList))
        `when`(excludedUserDao.findAll()).thenReturn(Single.just(excludedUserList))

        userRepository.list(1, 100)
            .test()
            .assertNoErrors()
            .assertValues(result)
    }
}