package com.islam97.android.apps.users.domain.usecase

import com.islam97.android.apps.users.domain.models.Result
import com.islam97.android.apps.users.domain.repositories.UsersRepository
import javax.inject.Inject

class GetAllUsersUseCase
@Inject constructor(private val usersRepository: UsersRepository) {
    suspend operator fun invoke(): Result {
        return usersRepository.getAllUsers()
    }
}