package com.islam97.android.apps.users.data.repositories

import com.islam97.android.apps.users.data.room.UsersDao
import com.islam97.android.apps.users.data.room.toEntity
import com.islam97.android.apps.users.domain.models.Result
import com.islam97.android.apps.users.domain.models.User
import com.islam97.android.apps.users.domain.repositories.UsersRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepositoryImpl
@Inject constructor(
    private val usersDao: UsersDao
) : UsersRepository {
    override suspend fun insert(user: User): Result {
        return try {
            usersDao.insert(user.toEntity())
            Result.Success("", Any())
        } catch (t: Throwable) {
            Result.Error("")
        }
    }
}