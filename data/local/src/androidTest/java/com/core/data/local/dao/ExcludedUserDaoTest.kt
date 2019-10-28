package com.core.data.local.dao

import com.core.data.local.BaseLocalTest
import com.core.data.local.model.ExcludedUserDB
import org.junit.Test

class ExcludedUserDaoTest : BaseLocalTest() {

    private lateinit var excludedUserDao: ExcludedUserDao

    private val excludedUsers = listOf(
        ExcludedUserDB("1"),
        ExcludedUserDB("2"),
        ExcludedUserDB("3"),
        ExcludedUserDB("4"),
        ExcludedUserDB("5")
    )

    override fun setUp() {
        super.setUp()

        excludedUserDao = database.excludedUserDao()
    }

    @Test
    fun excludeSeveralUsers() {
        excludedUserDao.insert(*excludedUsers.toTypedArray()).blockingAwait()
        excludedUserDao.findAll()
            .test()
            .assertNoErrors()
            .assertValue(excludedUsers)
    }
}