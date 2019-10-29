package com.core.data.repository

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.core.common.test.BaseTest
import com.core.data.local.dao.ExcludedUserDao
import com.core.data.local.model.ExcludedUserDB
import com.core.data.remote.AppGateway
import com.core.data.remote.entity.LoginEntity
import com.core.data.remote.entity.UserEntity
import com.core.data.repository.mapper.UserEntityDataMapper
import com.core.domain.Gender
import com.core.domain.User
import com.jakewharton.threetenabp.AndroidThreeTen
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

    private var userEntityList = listOf(
        UserEntity(login = LoginEntity("1")),
        UserEntity(login = LoginEntity("2")),
        UserEntity(login = LoginEntity("3")),
        UserEntity(login = LoginEntity("5")),
        UserEntity(login = LoginEntity("5"))
    )

    private var excludedUserList = listOf(
        ExcludedUserDB("1"),
        ExcludedUserDB("2")
    )

    lateinit var userRepository: UserRepositoryImpl

    @Before
    override fun setUp() {
        super.setUp()

        AndroidThreeTen.init(getContext())
        userRepository = UserRepositoryImpl(gateway, excludedUserDao, UserEntityDataMapper())
    }

    @Test
    fun `Get users from server and check that duplicates and excluded users were removed`() {
        `when`(gateway.users(anyInt(), anyInt())).thenReturn(Single.just(userEntityList))
        `when`(excludedUserDao.findAll()).thenReturn(Single.just(excludedUserList))

        userRepository.list(1, 100)
            .test()
            .assertNoErrors()
            .assertValue { true }
    }
}