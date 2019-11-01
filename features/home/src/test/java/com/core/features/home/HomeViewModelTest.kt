package com.core.features.home

import androidx.lifecycle.Observer
import com.core.common.android.Pager
import com.core.common.android.Resource
import com.core.common.test.BaseTest
import com.core.common.test.MockitoUtils
import com.core.domain.User
import com.core.features.home.model.UserModel
import com.core.features.home.usecase.ExcludeUserUseCase
import com.core.features.home.usecase.GetUsersPagedUseCase
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*

class HomeViewModelTest : BaseTest() {

    @Mock
    lateinit var getUsersPagedUseCase: GetUsersPagedUseCase
    @Mock
    lateinit var excludeUserUseCase: ExcludeUserUseCase
    @Mock
    lateinit var usersObserver: Observer<Resource<List<UserModel>>>
    @Mock
    lateinit var nameObserver: Observer<String>
    @Mock
    lateinit var surnameObserver: Observer<String>
    @Mock
    lateinit var emailObserver: Observer<String>
    @Mock
    private lateinit var throwableObserver: Observer<Throwable>

    private lateinit var homeViewModel: HomeViewModel
    
    private val throwable = Throwable("sometimes error happens")

    @Test
    fun `retrieve results from server`() {
        val pager = Pager<User>(1, 10)
        pager.addPageData(data = FakeData.users)

        `when`(getUsersPagedUseCase.execute(MockitoUtils.anyObject())).thenReturn(Single.just(pager))

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            users.observeForever(usersObserver)
            throwable.observeForever(throwableObserver)
        }
        verify(usersObserver).onChanged(Resource.success(FakeData.users.map { item -> UserModel(item) }))
        verify(throwableObserver, never()).onChanged(throwable)
    }

    @Test
    fun `retrieve empty results from server`() {
        val pager = Pager<User>(1, 10)
        pager.addPageData(data = mutableListOf())

        `when`(getUsersPagedUseCase.execute(MockitoUtils.anyObject())).thenReturn(Single.just(pager))

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            users.observeForever(usersObserver)
            throwable.observeForever(throwableObserver)
        }
        verify(usersObserver).onChanged(Resource.success(emptyList()))
        verify(throwableObserver, never()).onChanged(throwable)
    }

    @Test
    fun `catch error from server when we retrieve results`() {
        val pager = Pager<User>(1, 10)
        pager.addPageData(data = mutableListOf())

        `when`(getUsersPagedUseCase.execute(MockitoUtils.anyObject())).thenReturn(
            Single.error(throwable)
        )

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            users.observeForever(usersObserver)
            throwable.observeForever(throwableObserver)
        }
        verify(usersObserver).onChanged(Resource.error(throwable, emptyList()))
        verify(throwableObserver, never()).onChanged(throwable)
    }

    @Test
    fun `exclude user from list`() {
        val pager = Pager<User>(1, 10)
        pager.addPageData(data = FakeData.users)

        `when`(getUsersPagedUseCase.execute(MockitoUtils.anyObject())).thenReturn(Single.just(pager))
        `when`(excludeUserUseCase.execute(ArgumentMatchers.anyString())).thenReturn(Completable.complete())

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            users.observeForever(usersObserver)
            throwable.observeForever(throwableObserver)
        }
        verify(usersObserver).onChanged(Resource.success(FakeData.users.map { item -> UserModel(item) }))
        verify(throwableObserver, never()).onChanged(throwable)

        homeViewModel.onItemRemoved(UserModel(User("1")))

        val list = FakeData.users.toMutableList()
        list.removeIf { it.id == "1" }

        verify(usersObserver).onChanged(Resource.success(list.map { item -> UserModel(item) }))
        verify(throwableObserver, never()).onChanged(throwable)
    }

    @Test
    fun `filter results by name, surname and email`() {
        val pager = Pager<User>(1, 10)
        pager.addPageData(data = FakeData.users)

        `when`(getUsersPagedUseCase.execute(MockitoUtils.anyObject())).thenReturn(Single.just(pager))

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            users.observeForever(usersObserver)
            throwable.observeForever(throwableObserver)
            name.observeForever(nameObserver)
            surname.observeForever(surnameObserver)
            email.observeForever(emailObserver)
        }
        verify(usersObserver).onChanged(Resource.success(FakeData.users.map { item -> UserModel(item) }))
        verify(throwableObserver, never()).onChanged(throwable)

        homeViewModel.name.postValue("Lumi")
        verify(usersObserver).onChanged(
            Resource.success(
                FakeData.users.filter { it.name == "Lumi" }.map { item ->
                    UserModel(item)
                }
            )
        )

        homeViewModel.name.postValue("")
        homeViewModel.surname.postValue("Muller")
        verify(usersObserver).onChanged(
            Resource.success(
                FakeData.users.filter { it.surname == "Muller" }.map { item ->
                    UserModel(item)
                }
            )
        )

        homeViewModel.name.postValue("")
        homeViewModel.surname.postValue("")
        homeViewModel.email.postValue("nathan@gmail.com")
        verify(usersObserver).onChanged(
            Resource.success(
                FakeData.users.filter { it.email == "nathan@gmail.com" }.map { item ->
                    UserModel(item)
                }
            )
        )
    }

    @Test
    fun `filter results by name, letter to letter`() {
        val pager = Pager<User>(1, 10)
        pager.addPageData(data = FakeData.users)

        `when`(getUsersPagedUseCase.execute(MockitoUtils.anyObject())).thenReturn(Single.just(pager))

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            users.observeForever(usersObserver)
            throwable.observeForever(throwableObserver)
            name.observeForever(nameObserver)
        }
        verify(usersObserver).onChanged(Resource.success(FakeData.users.map { item -> UserModel(item) }))
        verify(throwableObserver, never()).onChanged(throwable)

        homeViewModel.name.postValue("a")
        homeViewModel.name.postValue("aa")
        homeViewModel.name.postValue("aaa")
        inOrder(usersObserver).apply {
            verify(usersObserver).onChanged(
                Resource.success(
                    FakeData.users.filter { it.name.startsWith("a") }.map { item ->
                        UserModel(item)
                    }
                )
            )
            verify(usersObserver).onChanged(
                Resource.success(
                    FakeData.users.filter { it.name.startsWith("aa") }.map { item ->
                        UserModel(item)
                    }
                )
            )
            verify(usersObserver).onChanged(
                Resource.success(
                    FakeData.users.filter { it.name.startsWith("aaa") }.map { item ->
                        UserModel(item)
                    }
                )
            )
        }
    }
}