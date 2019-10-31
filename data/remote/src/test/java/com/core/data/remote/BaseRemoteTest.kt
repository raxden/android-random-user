package com.core.data.remote

import com.core.common.test.BaseTest
import com.core.data.remote.retrofit.adapter.RxErrorHandlingCallAdapterFactory
import com.core.data.remote.retrofit.service.UserService
import com.google.gson.GsonBuilder
import okhttp3.mockwebserver.MockResponse
import org.junit.Before

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File

abstract class BaseRemoteTest : BaseTest() {

    private lateinit var server: MockWebServer
    protected lateinit var userDataSource: UserDataSource

    @Before
    override fun setUp() {
        super.setUp()
        initMockServer()
    }

    @After
    override fun tearDown() {
        super.tearDown()
        stopMockServer()
    }

    fun mockHttpResponse(fileName: String, responseCode: Int) = server.enqueue(
        MockResponse().apply {
            setResponseCode(responseCode)
            if (fileName.isNotEmpty())
                setBody(getJson(fileName))
        }
    )

    private fun getJson(path: String): String {
        return this.javaClass.classLoader?.getResource(path)?.let {
            val file = File(it.path)
            return String(file.readBytes())
        } ?: ""
    }

    private fun initMockServer() {
        server = MockWebServer()
        server.start()

        val service = Retrofit.Builder()
            .baseUrl(server.url(("/")))
            .addCallAdapterFactory(
                RxErrorHandlingCallAdapterFactory.create()
            )
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.create()
            )
            .addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().create())
            )
            .build()
            .create(UserService::class.java)

        userDataSource = UserDataSource(service)
    }

    private fun stopMockServer() {
        server.shutdown()
    }
}