package com.islam97.android.apps.users.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.islam97.android.apps.users.domain.models.Gender
import com.islam97.android.apps.users.domain.models.User

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "jogTitle") val jogTitle: String,
    @ColumnInfo(name = "gender") val gender: Gender
)

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = 0L,
        name = name,
        age = age,
        jogTitle = jogTitle,
        gender = gender
    )
}

fun UserEntity.toModel(): User {
    return User(
        id = 0L,
        name = name,
        age = age,
        jogTitle = jogTitle,
        gender = gender
    )
}
