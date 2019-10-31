package com.core.features.home

import com.core.common.android.Pager
import com.core.common.test.BaseTest
import com.core.domain.User
import com.core.domain.repository.UserRepository
import com.core.features.home.usecase.GetUsersPagedUseCase
import io.reactivex.Maybe
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*

class GetUsersPagedUseCaseTest : BaseTest() {

    @Mock
    lateinit var userRepository: UserRepository

    private lateinit var useCase: GetUsersPagedUseCase

    private var userList = listOf(
        User(id = "1"),
        User(id = "2"),
        User(id = "3"),
        User(id = "4"),
        User(id = "5")
    )

    private var userListWithDuplicates = listOf(
        User(id = "1"),
        User(id = "2"),
        User(id = "6")
    )

    override fun setUp() {
        super.setUp()

        useCase = GetUsersPagedUseCase(userRepository)
    }

    @Test
    fun `retrieve users from server, fill two pages and check that duplicates are removed`() {
        `when`(userRepository.list(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
            .thenReturn(Maybe.just(userList))

        val pager = Pager<User>(1, 10)

        useCase.execute(pager)
            .test()
            .assertNoErrors()
            .assertValue { it.getAllData() == userList }

        `when`(userRepository.list(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
            .thenReturn(Maybe.just(userListWithDuplicates))

        pager.next()

        useCase.execute(pager)
            .test()
            .assertNoErrors()
            .assertValue {
                it.getAllData() == listOf(
                    User(id = "1"),
                    User(id = "2"),
                    User(id = "3"),
                    User(id = "4"),
                    User(id = "5"),
                    User(id = "6")
                )
            }
    }
}