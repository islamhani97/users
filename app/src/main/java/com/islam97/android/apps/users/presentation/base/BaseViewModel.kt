package com.islam97.android.apps.users.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<S, I, NI : I> : ViewModel() {

    protected val mutableNavigationActionsFlow: MutableSharedFlow<NI> = MutableSharedFlow()
    val navigationActionsFlow: SharedFlow<NI> get() = mutableNavigationActionsFlow

    protected val mutableMessagesFlow: MutableSharedFlow<String> = MutableSharedFlow()
    val messagesFlow: SharedFlow<String> get() = mutableMessagesFlow

    protected abstract val mutableState: MutableStateFlow<S>
    val state: StateFlow<S> get() = mutableState

    abstract fun executeIntent(intent: I)
}