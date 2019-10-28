package com.core.data.repository

import androidx.lifecycle.Observer
import com.core.common.android.Resource
import com.core.data.BaseRepositoryTest
import com.core.data.local.dao.AccountDao
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AccountRepositoryTest: BaseRepositoryTest() {

    @MockK
    private lateinit var dao: AccountDao
    @RelaxedMockK
    private lateinit var accountObserver: Observer<Resource<Account>>
    @RelaxedMockK
    private lateinit var operationObserver: Observer<Resource<Boolean>>

    private lateinit var accountRepository: AccountRepositoryImpl

    private var fakeAccount = Account("username")

    @Before
    override fun setUp() {
        super.setUp()

        accountRepository = AccountRepositoryImpl(dao)
    }

    @Test
    fun `Get account from db`() {
        coEvery { dao.find(fakeAccount.username) } returns fakeAccount

        runBlocking {
            accountRepository.retrieve(fakeAccount.username).observeForever(accountObserver)
        }

        verifyOrder {
            accountObserver.onChanged(Resource.loading(null)) // Init loading with no value
            accountObserver.onChanged(Resource.success(fakeAccount)) // room data loaded
        }
        confirmVerified(accountObserver)
    }

    @Test
    fun `Save account in db`() {
        coEvery { dao.insert(fakeAccount) } returns Unit

        runBlocking {
            accountRepository.save(fakeAccount).observeForever(operationObserver)
        }

        verifyOrder {
            operationObserver.onChanged(Resource.loading(null)) // Init loading with no value
            operationObserver.onChanged(Resource.success(true)) // room data loaded
        }
        confirmVerified(accountObserver)
    }

    @Test
    fun `Remove account from db`() {
        coEvery { dao.delete(fakeAccount) } returns Unit

        runBlocking {
            accountRepository.remove(fakeAccount).observeForever(operationObserver)
        }

        verifyOrder {
            operationObserver.onChanged(Resource.loading(null)) // Init loading with no value
            operationObserver.onChanged(Resource.success(true)) // room data loaded
        }
        confirmVerified(accountObserver)
    }
}