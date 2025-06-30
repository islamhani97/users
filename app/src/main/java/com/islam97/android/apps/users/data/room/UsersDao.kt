package com.islam97.android.apps.users.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsersDao {
    @Insert
    fun insert(user: UserEntity)

    @Query("SELECT * FROM UserEntity")
    fun getAll(): List<UserEntity>
}