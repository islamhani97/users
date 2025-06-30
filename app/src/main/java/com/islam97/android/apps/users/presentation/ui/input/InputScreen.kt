package com.islam97.android.apps.users.presentation.ui.input

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

@Serializable
data object RouteInputScreen

@Composable
fun InputScreen(navController: NavHostController) {
}

@Preview(showBackground = true)
@Composable
fun PreviewInputScreen() {
    InputScreen(navController = NavHostController(LocalContext.current))
}