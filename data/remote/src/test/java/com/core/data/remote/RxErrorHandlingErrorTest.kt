package com.core.data.remote

import com.core.data.remote.entity.ErrorEntity
import com.core.data.remote.retrofit.exception.RetrofitException
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import java.net.HttpURLConnection

class RxErrorHandlingErrorTest : BaseRemoteTest() {

    @Test
    fun `throw client error and catch it`() {
        mockHttpResponse("error.json", HttpURLConnection.HTTP_BAD_REQUEST)
        gateway.users(1, 100).test().assertError {
            (it as RetrofitException).kind == RetrofitException.Kind.CLIENT_ERROR &&
                    it.parseBodyErrorAs(ErrorEntity::class.java)?.let { entity ->
                        entity.error == "Uh oh, something has gone wrong. Please tweet us @randomapi about the issue. Thank you."
                    } ?: false
        }
    }

    @Test
    fun `throw unauthenticated error and catch it`() {
        mockHttpResponse("", HttpURLConnection.HTTP_UNAUTHORIZED)
        gateway.users(1, 100).test().assertError {
            (it as RetrofitException).kind == RetrofitException.Kind.UNAUTHENTICATED
        }
    }

    @Test
    fun `throw server error and catch it`() {
        mockHttpResponse("", HttpURLConnection.HTTP_INTERNAL_ERROR)
        gateway.users(1, 100).test().assertError {
            (it as RetrofitException).kind == RetrofitException.Kind.SERVER_ERROR
        }
    }

    @Test
    fun `throw unexpected error and catch it`() {
        mockHttpResponse("", 600)
        gateway.users(1, 100).test().assertError {
            (it as RetrofitException).kind == RetrofitException.Kind.UNEXPECTED
        }
    }
}