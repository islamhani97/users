package com.islam97.android.apps.users.core.di.modules

import com.islam97.android.apps.users.data.repositories.UsersRepositoryImpl
import com.islam97.android.apps.users.domain.repositories.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideUsersRepository(repository: UsersRepositoryImpl): UsersRepository {
        return repository
    }
}