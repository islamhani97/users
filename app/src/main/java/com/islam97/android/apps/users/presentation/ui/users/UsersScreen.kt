package com.islam97.android.apps.users.presentation.ui.users

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

@Serializable
data object RouteUsersScreen

@Composable
fun UsersScreen(navController: NavHostController) {
}

@Preview(showBackground = true)
@Composable
fun PreviewUsersScreen() {
    UsersScreen(navController = NavHostController(LocalContext.current))
}