package com.islam97.android.apps.users.presentation.ui.users

import androidx.lifecycle.viewModelScope
import com.islam97.android.apps.users.domain.models.Result
import com.islam97.android.apps.users.domain.models.User
import com.islam97.android.apps.users.domain.usecase.GetAllUsersUseCase
import com.islam97.android.apps.users.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel
@Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase
) : BaseViewModel<UsersState, UsersIntent, UsersIntent.NavigationIntent>() {
    override val mutableState: MutableStateFlow<UsersState> = MutableStateFlow(UsersState())

    init {
        executeIntent(UsersIntent.GetAllUsers)
    }

    override fun executeIntent(intent: UsersIntent) {
        viewModelScope.launch {
            when (intent) {
                is UsersIntent.GetAllUsers -> {
                    mutableState.value = mutableState.value.copy(isLoading = true)
                    getAllUsers()
                }

                is UsersIntent.NavigationIntent -> {
                    mutableNavigationActionsFlow.emit(intent)
                }
            }
        }
    }

    private fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getAllUsersUseCase.invoke()) {
                is Result.Success<*> -> {
                    mutableState.value = mutableState.value.copy(
                        isLoading = false, users = result.data as List<User>
                    )
                }

                is Result.Error -> {
                    mutableState.value = mutableState.value.copy(isLoading = false)
                    mutableMessagesFlow.emit(result.errorMessage ?: "")
                }
            }
        }
    }
}

data class UsersState(
    val isLoading: Boolean = false, val users: List<User> = listOf()
)

sealed class UsersIntent {
    data object GetAllUsers : UsersIntent()
    sealed class NavigationIntent : UsersIntent() {
        data object Back : NavigationIntent()
    }
}