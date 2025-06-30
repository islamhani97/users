package com.islam97.android.apps.users.presentation.ui.input

import androidx.lifecycle.viewModelScope
import com.islam97.android.apps.users.domain.models.Gender
import com.islam97.android.apps.users.domain.models.Result
import com.islam97.android.apps.users.domain.models.User
import com.islam97.android.apps.users.domain.usecase.AddUserUseCase
import com.islam97.android.apps.users.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputViewModel
@Inject constructor(
    private val addUserUseCase: AddUserUseCase
) : BaseViewModel<InputState, InputIntent, InputIntent.NavigationIntent>() {
    override val mutableState: MutableStateFlow<InputState> = MutableStateFlow(InputState())
    override fun executeIntent(intent: InputIntent) {
        viewModelScope.launch {
            when (intent) {
                is InputIntent.SetName -> {
                    mutableState.value = mutableState.value.copy(name = intent.name)
                }

                is InputIntent.SetAge -> {
                    mutableState.value = mutableState.value.copy(age = intent.age)
                }

                is InputIntent.SetJobTitle -> {
                    mutableState.value = mutableState.value.copy(jobTitle = intent.jobTitle)
                }

                is InputIntent.SetGender -> {
                    mutableState.value = mutableState.value.copy(gender = intent.gender)
                }

                is InputIntent.AddUser -> {
                    if (isDataValid()) {
                        mutableState.value = mutableState.value.copy(isLoading = true)
                        addUser(
                            User(
                                name = mutableState.value.name,
                                age = mutableState.value.age,
                                jogTitle = mutableState.value.jobTitle,
                                gender = mutableState.value.gender!!
                            )
                        )
                    }
                }

                is InputIntent.NavigationIntent -> {
                    mutableNavigationActionsFlow.emit(intent)
                }
            }
        }
    }

    private fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = addUserUseCase.invoke(user)) {
                is Result.Success<*> -> {
                    mutableState.value = InputState()
                    mutableMessagesFlow.emit("User Added Successfully")
                }

                is Result.Error -> {
                    mutableState.value = mutableState.value.copy(isLoading = false)
                    mutableMessagesFlow.emit(result.errorMessage ?: "")
                }
            }
        }
    }

    private suspend fun isDataValid(): Boolean {
        if (mutableState.value.name.isBlank()) {
            mutableMessagesFlow.emit("Name is Required")
            return false
        }

        if (mutableState.value.age < 1) {
            mutableMessagesFlow.emit("Age is Required")
            return false
        }

        if (mutableState.value.jobTitle.isBlank()) {
            mutableMessagesFlow.emit("Job Title is Required")
            return false
        }

        if (mutableState.value.gender == null) {
            mutableMessagesFlow.emit("Select Gender")
            return false
        }
        return true
    }
}

data class InputState(
    val isLoading: Boolean = false,
    val name: String = "",
    val age: Int = 0,
    val jobTitle: String = "",
    val gender: Gender? = null,
)

sealed class InputIntent {
    data class SetName(val name: String) : InputIntent()
    data class SetAge(val age: Int) : InputIntent()
    data class SetJobTitle(val jobTitle: String) : InputIntent()
    data class SetGender(val gender: Gender) : InputIntent()
    data object AddUser : InputIntent()
    sealed class NavigationIntent : InputIntent() {
        data object NavigateToUsersScreen : NavigationIntent()
    }
}