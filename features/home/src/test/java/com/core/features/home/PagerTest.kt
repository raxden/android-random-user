package com.core.features.home

import com.core.common.android.Pager
import com.core.common.test.BaseTest
import org.junit.Assert
import org.junit.Test

class PagerTest : BaseTest() {

    private lateinit var pager: Pager<Int>

    private val page = 1
    private val pageSize = 100

    override fun setUp() {
        super.setUp()

        pager = Pager(page, pageSize)
    }

    @Test
    fun `check constructor`() {
        Assert.assertEquals(pager.currentPage(), page)
        Assert.assertEquals(pager.pageSize(), pageSize)
        Assert.assertEquals(pager.hasMoreResults(), true)
        Assert.assertEquals(pager.hasPageData(), false)
        Assert.assertEquals(pager.isFirstPage(), true)
    }

    @Test
    fun `verify that item is removed from pager`() {
        pager.addPageData(data = mutableListOf(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        ))
        pager.remove(3)
        Assert.assertEquals(pager.getAllData(), listOf(
            1, 2, 4, 5, 6, 7, 8, 9, 10
        ))
    }

    @Test
    fun `verify that data is removed from pager when clear operation is requested`() {
        pager.addPageData(data = mutableListOf(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        ))
        Assert.assertEquals(pager.hasPageData(), true)
        pager.clear()
        Assert.assertEquals(pager.hasPageData(), false)
    }

    @Test
    fun `verify that pager change to next page when next operation is requested`() {
        pager.addPageData(data = mutableListOf(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        ))
        Assert.assertEquals(pager.hasPageData(), true)
        pager.next()
        Assert.assertEquals(pager.isFirstPage(), false)
        Assert.assertEquals(pager.currentPage(), 2)
        Assert.assertEquals(pager.hasPageData(), false)
        pager.addPageData(data = mutableListOf(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        ))
        pager.next()
        Assert.assertEquals(pager.isFirstPage(), false)
        Assert.assertEquals(pager.currentPage(), 3)
        assert(pager.getAllData().size == 20)
    }
}