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
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.ArgumentCaptor



class HomeViewModelTest : BaseTest() {

    @Mock
    lateinit var getUsersPagedUseCase: GetUsersPagedUseCase
    @Mock
    lateinit var excludeUserUseCase: ExcludeUserUseCase
    @Mock
    lateinit var pager: Pager<User>
    @Mock
    lateinit var usersObserver: Observer<Resource<List<UserModel>>>
    @Mock
    private lateinit var throwableObserver: Observer<Throwable>

    private lateinit var homeViewModel: HomeViewModel

    private val throwable = Throwable("sometimes error happens")

    @Test
    fun `retrieve results from server`() {
        val data = mutableListOf(
            User("1"),
            User("2")
        )
        val pager = Pager<User>(1, 10)
        pager.addPageData(data = data)

        `when`(getUsersPagedUseCase.execute(MockitoUtils.anyObject())).thenReturn(Single.just(pager))

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            users.observeForever(usersObserver)
            throwable.observeForever(throwableObserver)
        }
        verify(usersObserver).onChanged(Resource.success(data.map { item -> UserModel(item) }))
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

        `when`(getUsersPagedUseCase.execute(MockitoUtils.anyObject())).thenReturn(Single.error(throwable))

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            users.observeForever(usersObserver)
            throwable.observeForever(throwableObserver)
        }
        verify(usersObserver).onChanged(Resource.error(throwable, emptyList()))
        verify(throwableObserver, never()).onChanged(throwable)
    }

    @Test
    fun `exclude user from list`() {
        val data = mutableListOf(
            User("1"),
            User("2")
        )
        val pager = Pager<User>(1, 10)
        pager.addPageData(data = data)

        `when`(getUsersPagedUseCase.execute(MockitoUtils.anyObject())).thenReturn(Single.just(pager))
        `when`(excludeUserUseCase.execute(ArgumentMatchers.anyString())).thenReturn(Completable.complete())

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            users.observeForever(usersObserver)
            throwable.observeForever(throwableObserver)
        }
        verify(usersObserver).onChanged(Resource.success(data.map { item -> UserModel(item) }))
        verify(throwableObserver, never()).onChanged(throwable)

        homeViewModel.onItemRemoved(UserModel(User("1")))

        verify(usersObserver).onChanged(Resource.success(mutableListOf(UserModel(User("2")))))
        verify(throwableObserver, never()).onChanged(throwable)
    }
}