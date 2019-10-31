package com.core.features.home.usecase

import com.core.common.android.Pager
import com.core.domain.User
import com.core.domain.repository.UserRepository
import io.reactivex.Maybe
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class GetUsersPagedUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    private val ids: MutableSet<String> = mutableSetOf()

    fun execute(pager: Pager<User>): Single<Pager<User>> {
        return userRepository.list(pager.currentPage(), pager.pageSize())
            .map { it.toMutableList() }
            .map { list ->
                list.removeAll { user -> ids.contains(user.id) }
                list
            }
            .map { list ->
                ids.addAll(list.map { it.id })
                list
            }
            .map {
                pager.addPageData(data = it)
            }
            .doOnSubscribe {
                pager.setRequestPage(true)
            }
            .doOnSuccess {
                pager.setRequestPage(false)
            }
            .doOnComplete {
                pager.setRequestPage(false)
                pager.setMoreResults(false)
            }
            .doOnError {
                pager.setRequestPage(false)
                pager.setMoreResults(false)
            }
            .toSingle(pager)
    }
}