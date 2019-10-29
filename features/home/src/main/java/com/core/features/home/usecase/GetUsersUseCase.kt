package com.core.features.home.usecase

import com.core.domain.User
import com.core.domain.repository.UserRepository
import io.reactivex.Maybe
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    fun execute(page: Int, results: Int): Maybe<List<User>> {
        return userRepository.list(page, results)
    }
}