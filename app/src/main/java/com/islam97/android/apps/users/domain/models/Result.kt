package com.islam97.android.apps.users.domain.models

sealed class Result {
    data class Success<T>(val message: String?, val data: T) : Result()
    data class Error(val errorMessage: String?) : Result()
}