package com.core.features.home

import androidx.lifecycle.Observer
import com.core.common.android.Pager
import com.core.common.test.BaseTest
import com.core.common.test.MockitoUtils
import com.core.domain.User
import com.core.features.home.usecase.ExcludeUserUseCase
import com.core.features.home.usecase.GetUsersPagedUseCase
import io.reactivex.Single
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*

class HomeViewModelTest: BaseTest() {

    @Mock
    lateinit var getUsersPagedUseCase: GetUsersPagedUseCase
    @Mock
    lateinit var excludeUserUseCase: ExcludeUserUseCase
    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    private lateinit var homeViewModel: HomeViewModel

    private val firstPage = Pager<User>(1, 20)

    override fun setUp() {
        super.setUp()


    }

    @Test
    fun `retrieve results from server`() {
        `when`(getUsersPagedUseCase.execute(MockitoUtils.anyObject())).thenReturn(Single.just(firstPage))

        homeViewModel = HomeViewModel(getUsersPagedUseCase, excludeUserUseCase).apply {
            loading.observeForever(loadingObserver)
        }

//        verify(loadingObserver, times(2)).onChanged(false)

        inOrder(loadingObserver).apply {
            verify(loadingObserver).onChanged(true)
            verify(loadingObserver).onChanged(false)
        }
    }
}