package com.islam97.android.apps.users.domain.repositories

import com.islam97.android.apps.users.domain.models.Result
import com.islam97.android.apps.users.domain.models.User

interface UsersRepository {
    suspend fun insertUser(user: User): Result
    fun getAllUsers(): Result
}