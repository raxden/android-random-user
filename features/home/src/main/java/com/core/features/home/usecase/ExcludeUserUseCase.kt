package com.core.features.home.usecase

import com.core.domain.repository.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

class ExcludeUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    fun execute(id: String): Completable {
        return userRepository.exclude(id)
    }
}