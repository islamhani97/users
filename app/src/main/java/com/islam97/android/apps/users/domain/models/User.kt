package com.islam97.android.apps.users.domain.models

import com.islam97.android.apps.users.presentation.utils.Gender

data class User(
    val id: Long,
    val name: String,
    val age: Int,
    val jogTitle: String,
    val gender: Gender
)