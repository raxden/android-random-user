package com.core.features.splash

import com.core.domain.repository.UserRepository
import javax.inject.Inject

class ForwardUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository
) {

//    fun execute(): LiveData<Resource<User>> {
//        return liveData {
//            emit(Resource.loading())
//            accountRepository.retrieve().switchMap {resource ->
//                if (resource.status == Status.SUCCESS) {
//                    emitSource(userRepository.retrieve(resource.data.username))
//                } else if (resource.status == Status.ERROR) {
//                    emit(Resource.error(resource.throwable))
//                }
//            }
//            emitSource(
//                accountRepository.retrieve().switchMap { resource ->
//                    liveData<Resource<User>> {
//                        resource.data?.let {
//                            userRepository.retrieve(it.username)
//                        } ?: emit(Resource.error(resource.throwable!!))
//                    }
//                }
//            )
//        }
//        return accountRepository.retrieve().switchMap { account ->
//            liveData<Resource<User>> {
//                account.data?.username?.let {
//                    userRepository.retrieve(it)
//                }
//            }
//        }
//        return Transformations.map(accountRepository.retrieve()) {
//            userRepository.retrieve(it.data)
//        }
//        return Transformations.switchMap(accountRepository.retrieve()) {
//            userRepository.retrieve(it.data)
//        }
//        return MediatorLiveData<Resource<User>>().apply {
//            addSource(accountRepository.retrieve()) {
//                userRepository.retrieve(it.data)
//            }
//        }
//    }
}