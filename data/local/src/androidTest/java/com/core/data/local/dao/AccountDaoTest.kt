package com.core.data.local.dao

import com.core.data.local.BaseLocalTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AccountDaoTest : BaseLocalTest() {

    private lateinit var accountDao: AccountDao

    private val fakeAccount = Account("username")

    override fun setUp() {
        super.setUp()

        accountDao = database.accountDao()

        runBlocking {
            accountDao.insert(fakeAccount)
        }
    }

    @Test
    fun retrieveUserFromDB() = runBlocking {
        val account = database.accountDao().find(fakeAccount.username)
        compareUsers(fakeAccount, account)
    }

    @Test
    fun retrieveUserThatNotExistsInDB() = runBlocking {
        val account = database.accountDao().find("username_not_exists")
        assertNull(account)
    }

    private fun compareUsers(account: Account, accountToCompare: Account) {
        assertEquals(account.username, accountToCompare.username)
    }
}