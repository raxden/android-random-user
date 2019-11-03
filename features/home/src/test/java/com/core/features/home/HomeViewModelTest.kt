package com.core.features.home

import androidx.lifecycle.Observer
import com.core.common.android.Pager
import com.core.common.android.Resource
import com.core.common.test.BaseTest
import com.core.domain.User
import com.core.features.home.model.UserModel
import com.core.features.home.usecase.ExcludeUserUseCase
import com.core.features.home.usecase.GetUsersPagedUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test

class HomeViewModelTest : BaseTest() {

    @MockK
    lateinit var getUsersPagedUseCase: GetUsersPagedUseCase
    @MockK
    lateinit var excludeUserUseCase: ExcludeUserUseCase
    @RelaxedMockK
    lateinit var usersObserver: Observer<Resource<List<UserModel>>>
    @RelaxedMockK
    lateinit var nameObserver: Observer<String>
    @RelaxedMockK
    lateinit var surnameObserver: Observer<String>
    @RelaxedMockK
    lateinit var emailObserver: Observer<String>
    @MockK
    private lateinit var throwableObserver: Observer<Throwable>

    private lateinit var homeViewModel: HomeViewModel
    
    private val throwable = Throwable("sometimes error happens")

    @Test
    fun `retrieve results from server`() {
        val pager = Pager<User>(1, 10)
        pager.addPageData(data = FakeUserData.users)

        every { getUsersPagedUseCase.execute(any()) } returns Single.just(pager)

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            users.observeForever(usersObserver)
            throwable.observeForever(throwableObserver)
        }
        verify {
            usersObserver.onChanged(Resource.success(FakeUserData.users.map { item -> UserModel(item) }))
            throwableObserver wasNot Called
        }
        confirmVerified(usersObserver)
    }

    @Test
    fun `retrieve empty results from server`() {
        val pager = Pager<User>(1, 10)
        pager.addPageData(data = mutableListOf())

        every { getUsersPagedUseCase.execute(any()) } returns Single.just(pager)

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            users.observeForever(usersObserver)
            throwable.observeForever(throwableObserver)
        }
        verify {
            usersObserver.onChanged(Resource.success(emptyList()))
            throwableObserver wasNot Called
        }
        confirmVerified(usersObserver)
    }

    @Test
    fun `catch error from server when we retrieve results`() {
        val pager = Pager<User>(1, 10)
        pager.addPageData(data = mutableListOf())

        every { getUsersPagedUseCase.execute(any()) } returns Single.error(throwable)

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            users.observeForever(usersObserver)
            throwable.observeForever(throwableObserver)
        }
        verify {
            usersObserver.onChanged(Resource.error(throwable, emptyList()))
            throwableObserver wasNot Called
        }
        confirmVerified(usersObserver)
    }

    @Test
    fun `exclude user from list`() {
        val pager = Pager<User>(1, 10)
        pager.addPageData(data = FakeUserData.users)

        every { getUsersPagedUseCase.execute(any()) } returns Single.just(pager)
        every { excludeUserUseCase.execute(any()) } returns Completable.complete()

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            users.observeForever(usersObserver)
            throwable.observeForever(throwableObserver)
        }
        verify {
            usersObserver.onChanged(Resource.success(FakeUserData.users.map { item -> UserModel(item) }))
            throwableObserver wasNot Called
        }
        confirmVerified(usersObserver)

        homeViewModel.onItemRemoved(UserModel(User("1")))

        val list = FakeUserData.users.toMutableList()
        list.removeIf { it.id == "1" }

        verify {
            usersObserver.onChanged(Resource.success(list.map { item -> UserModel(item) }))
            throwableObserver wasNot Called
        }
        confirmVerified(usersObserver)
    }

    @Test
    fun `filter results by name`() {
        val pager = Pager<User>(1, 10)
        pager.addPageData(data = FakeUserData.users)

        every { getUsersPagedUseCase.execute(any()) } returns Single.just(pager)

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            users.observeForever(usersObserver)
            throwable.observeForever(throwableObserver)
            name.observeForever(nameObserver)
        }

        homeViewModel.name.postValue("Lumi")

        verifyOrder {
            usersObserver.onChanged(Resource.success(FakeUserData.users.map { item -> UserModel(item) }))
            usersObserver.onChanged(Resource.success(
                FakeUserData.users.filter { it.name.startsWith("Lumi") }.map { item ->
                    UserModel(item)
                }
            ))
            throwableObserver wasNot Called
        }
        confirmVerified(usersObserver)
    }

    @Test
    fun `filter results by surname`() {
        val pager = Pager<User>(1, 10)
        pager.addPageData(data = FakeUserData.users)

        every { getUsersPagedUseCase.execute(any()) } returns Single.just(pager)

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            users.observeForever(usersObserver)
            throwable.observeForever(throwableObserver)
            surname.observeForever(surnameObserver)
        }

        homeViewModel.surname.postValue("Muller")

        verifyOrder {
            usersObserver.onChanged(Resource.success(FakeUserData.users.map { item -> UserModel(item) }))
            usersObserver.onChanged(Resource.success(
                FakeUserData.users.filter { it.surname.startsWith("Muller") }.map { item ->
                    UserModel(item)
                }
            ))
            throwableObserver wasNot Called
        }
        confirmVerified(usersObserver)
    }

    @Test
    fun `filter results by email`() {
        val pager = Pager<User>(1, 10)
        pager.addPageData(data = FakeUserData.users)

        every { getUsersPagedUseCase.execute(any()) } returns Single.just(pager)

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            users.observeForever(usersObserver)
            throwable.observeForever(throwableObserver)
            email.observeForever(emailObserver)
        }

        homeViewModel.email.postValue("nathan@gmail.com")

        verifyOrder {
            usersObserver.onChanged(Resource.success(FakeUserData.users.map { item -> UserModel(item) }))
            usersObserver.onChanged(Resource.success(
                FakeUserData.users.filter { it.email.startsWith("nathan@gmail.com") }.map { item ->
                    UserModel(item)
                }
            ))
            throwableObserver wasNot Called
        }
        confirmVerified(usersObserver)
    }

    @Test
    fun `filter results by name, letter to letter`() {
        val pager = Pager<User>(1, 10)
        pager.addPageData(data = FakeUserData.users)

        every { getUsersPagedUseCase.execute(any()) } returns Single.just(pager)

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            users.observeForever(usersObserver)
            throwable.observeForever(throwableObserver)
            name.observeForever(emailObserver)
        }

        homeViewModel.name.postValue("a")
        homeViewModel.name.postValue("aa")
        homeViewModel.name.postValue("aaa")

        verifyOrder {
            usersObserver.onChanged(Resource.success(FakeUserData.users.map { item -> UserModel(item) }))
            usersObserver.onChanged(Resource.success(
                FakeUserData.users.filter { it.name.startsWith("a") }.map { item ->
                    UserModel(item)
                }
            ))
            usersObserver.onChanged(Resource.success(
                FakeUserData.users.filter { it.name.startsWith("aa") }.map { item ->
                    UserModel(item)
                }
            ))
            usersObserver.onChanged(Resource.success(
                FakeUserData.users.filter { it.name.startsWith("aaa") }.map { item ->
                    UserModel(item)
                }
            ))
            throwableObserver wasNot Called
        }
        confirmVerified(usersObserver)
    }
}