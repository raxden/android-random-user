package com.core.data.local

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.core.common.test.BaseTest
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseLocalTest: BaseTest() {

    protected lateinit var database: AppDatabase

    override fun setUp() {
        super.setUp()

        database = Room
            .inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, AppDatabase::class.java)
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()
    }

    override fun tearDown() {
        super.tearDown()

        database.close()
    }
}