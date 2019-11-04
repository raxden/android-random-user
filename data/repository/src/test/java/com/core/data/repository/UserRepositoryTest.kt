package com.core.data.repository

import com.core.common.test.BaseTest
import com.core.data.local.dao.ExcludedUserDao
import com.core.data.local.model.ExcludedUserDB
import com.core.data.remote.UserDataSource
import com.core.data.remote.entity.LoginEntity
import com.core.data.remote.entity.UserEntity
import com.core.data.repository.mapper.UserEntityDataMapper
import com.core.domain.User
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class UserRepositoryTest : BaseTest() {

    @MockK
    lateinit var userDataSource: UserDataSource
    @MockK
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

        userRepository = UserRepositoryImpl(userDataSource, excludedUserDao, UserEntityDataMapper())
    }

    @Test
    fun `Get users from server and check that duplicates and excluded users were removed`() {
        every { userDataSource.users(1, 100) } returns Single.just(userEntityList)
        every { excludedUserDao.findAll() } returns Single.just(excludedUserList)

        userRepository.list(1, 100)
            .test()
            .assertNoErrors()
            .assertValue {
                checkDuplicates(it) && checkExcludedUsers(it)
            }
    }

    @Test
    fun `Get empty users from server and check that completed is called`() {
        every { userDataSource.users(1, 100) } returns Single.just(emptyList())
        every { excludedUserDao.findAll() } returns Single.just(excludedUserList)

        userRepository.list(1, 100)
            .test()
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `Get error from server and check that error is called`() {
        val throwable = Exception("ops!")
        every { userDataSource.users(1, 100) } returns Single.error(throwable)
        every { excludedUserDao.findAll() } returns Single.just(excludedUserList)

        userRepository.list(1, 100)
            .test()
            .assertError(throwable)
    }

    private fun checkDuplicates(users: List<User>): Boolean {
        return users.groupingBy { it.id }.eachCount().filter { it.value > 1 }.isEmpty()
    }

    private fun checkExcludedUsers(users: List<User>): Boolean {
        excludedUserList.forEach {excludedUser ->
            users.find { it.id == excludedUser.id }?.let { return false }
        }
        return true
    }
}