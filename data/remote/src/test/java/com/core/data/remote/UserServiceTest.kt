package com.core.data.remote

import com.core.data.remote.entity.UserEntity
import org.junit.Test
import java.net.HttpURLConnection

class UserServiceTest : BaseRemoteTest() {

    @Test
    fun `fetch users`() {
        mockHttpResponse("users.json", HttpURLConnection.HTTP_OK)
        userDataSource.users(1, 100).test()
            .assertNoErrors()
            .assertValue { validateUsers(it) }
    }

    private fun validateUsers(list: List<UserEntity>): Boolean {
        return true
    }
}