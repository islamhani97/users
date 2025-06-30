package com.islam97.android.apps.users.presentation.ui.users

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

@Serializable
data object RouteUsersScreen

@Composable
fun UsersScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: UsersViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationActionsFlow.collect {
            when (it) {
                is UsersIntent.NavigationIntent.Back -> {
                    navController.navigateUp()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.messagesFlow.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val (toolbarReference, loadingIndicatorReference, noDataReference, usersReference) = createRefs()
            Row(modifier = Modifier
                .constrainAs(toolbarReference) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(0.9f)
                .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            viewModel.executeIntent(UsersIntent.NavigationIntent.Back)
                        },
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "All Users",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .constrainAs(
                                loadingIndicatorReference
                            ) {
                                top.linkTo(toolbarReference.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }
                            .size(60.dp))
                }

                state.users.isEmpty() -> {
                    Text(
                        modifier = Modifier.constrainAs(noDataReference) {
                            top.linkTo(toolbarReference.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }, text = "No Users"
                    )
                }

                else -> {
                    LazyColumn(modifier = Modifier
                        .constrainAs(usersReference) {
                            top.linkTo(toolbarReference.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                            height = Dimension.fillToConstraints
                        }
                        .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        items(state.users.size) { index ->
                            UserItem(
                                modifier = Modifier.fillMaxWidth(0.9f), user = state.users[index]
                            )
                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUsersScreen() {
    UsersScreen(navController = NavHostController(LocalContext.current))
}