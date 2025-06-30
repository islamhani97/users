package com.islam97.android.apps.users.domain.models

data class User(
    val id: Long = 0L,
    val name: String,
    val age: Int,
    val jogTitle: String,
    val gender: Gender
)