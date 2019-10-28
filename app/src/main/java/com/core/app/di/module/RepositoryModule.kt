package com.core.app.di.module

import com.core.data.repository.UserRepositoryImpl
import com.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    internal abstract fun accountRepository(repository: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    internal abstract fun projectRepository(repository: ProjectRepositoryImpl): ProjectRepository

    @Binds
    @Singleton
    internal abstract fun userRepository(repository: UserRepositoryImpl): UserRepository
}
