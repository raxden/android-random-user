package com.core.common.android

import timber.log.Timber

class Pager<T> constructor(
        private var page: Int = 0,
        private var pageSize: Int = 50
) {

    private var initPage: Int = page
    private var moreResults: Boolean = true
    private var pageData: MutableMap<Int, MutableList<T>> = mutableMapOf()
    private var pageLoading: MutableMap<Int, Boolean> = mutableMapOf()

    fun addPageData(page: Int? = null, data: MutableList<T>): Pager<T> {
        if (page != null)
            pageData[page] = data
        else
            pageData[this.page] = data
        return this
    }

    fun hasPageData(page: Int? = null) = getPageData(page).isNotEmpty()

    fun getPageData(page: Int? = null) = page?.let {
        pageData[page] ?: mutableListOf()
    } ?: pageData[this.page] ?: mutableListOf()

    fun getAllData() = mutableListOf<T>().also { list ->
        pageData.values.forEach { list.addAll(it) }
    }

    fun remove(item: T) = pageData.values.forEach { it.remove(item) }

    fun next(): Pager<T> {
        page = page.inc()
        return this
    }

    fun restart(): Pager<T> {
        page = initPage
        pageData.clear()
        moreResults = true
        return this
    }

    fun clear(): Pager<T> {
        page = initPage
        pageData.clear()
        moreResults = true
        return this
    }

    fun setMoreResults(value: Boolean) {
        moreResults = value
    }

    fun setRequestPage(value: Boolean) {
        pageLoading[page] = value
        Timber.d("PAGER: setRequestPage $value")
    }

    fun currentPage() = page

    fun pageSize() = pageSize

    fun hasMoreResults() = moreResults

    fun isFirstPage() = initPage == page

    fun isPageRequested() = pageLoading[page] == true
}
