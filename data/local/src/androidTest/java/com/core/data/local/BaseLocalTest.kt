package com.core.data.local

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.core.common.test.BaseTest

abstract class BaseLocalTest: BaseTest() {

    protected lateinit var database: AppDatabase

    override fun setUp() {
        super.setUp()

        database = Room
            .inMemoryDatabaseBuilder(getContext(), AppDatabase::class.java)
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()
    }

    override fun tearDown() {
        super.tearDown()

        database.close()
    }

    private fun getContext() = InstrumentationRegistry.getInstrumentation().context
}