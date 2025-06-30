package com.islam97.android.apps.users.presentation.ui.input

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.islam97.android.apps.users.domain.models.Gender
import com.islam97.android.apps.users.presentation.ui.users.RouteUsersScreen
import kotlinx.serialization.Serializable

@Serializable
data object RouteInputScreen

@Composable
fun InputScreen(navController: NavHostController) {

    val context = LocalContext.current
    val viewModel: InputViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.navigationActionsFlow.collect {
            when (it) {
                is InputIntent.NavigationIntent.NavigateToUsersScreen -> {
                    navController.navigate(RouteUsersScreen)
                }
            }
        }
    }

    LaunchedEffect(state.message) {
        state.message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val (subtitleReference, nameReference, ageReference, jobTitleReference, genderTitleReference, genderReference, saveReference) = createRefs()
            val startGuideline = createGuidelineFromStart(0.05f)
            val endGuideline = createGuidelineFromEnd(0.05f)

            Text(
                modifier = Modifier
                    .constrainAs(subtitleReference) {
                        top.linkTo(parent.top)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 16.dp), text = "Enter user details")
            OutlinedTextField(
                modifier = Modifier
                    .constrainAs(nameReference) {
                        top.linkTo(subtitleReference.bottom)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 16.dp),
                value = state.name,
                onValueChange = { viewModel.executeIntent(InputIntent.SetName(it)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                label = { Text("Name") },
                placeholder = { Text("Ex: John Smith") })

            OutlinedTextField(
                modifier = Modifier
                    .constrainAs(ageReference) {
                        top.linkTo(nameReference.bottom)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 16.dp),
                value = if (state.age > 0) "${state.age}" else "",
                onValueChange = {
                    if (it.length < 4 && it.all { digit -> digit.isDigit() }) {
                        viewModel.executeIntent(InputIntent.SetAge(if (it.isNotBlank()) it.toInt() else 0))
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                ),
                label = { Text("Age") },
                placeholder = { Text("Ex: 30") })

            OutlinedTextField(
                modifier = Modifier
                    .constrainAs(
                        jobTitleReference
                    ) {
                        top.linkTo(ageReference.bottom)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 16.dp),
                value = state.jobTitle,
                onValueChange = { viewModel.executeIntent(InputIntent.SetJobTitle(it)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                label = { Text("Job Title") },
                placeholder = { Text("Ex: Android Developer") })

            Text(
                modifier = Modifier
                    .constrainAs(genderTitleReference) {
                        top.linkTo(jobTitleReference.bottom)
                        start.linkTo(startGuideline)
                    }
                    .padding(top = 16.dp),
                text = "Select Gender",
                style = MaterialTheme.typography.titleMedium)
            Column(
                modifier = Modifier
                    .constrainAs(genderReference) {
                        top.linkTo(genderTitleReference.bottom)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 8.dp)
                    .selectableGroup(),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Gender.entries.forEach { gender ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (gender == state.gender),
                                onClick = { viewModel.executeIntent(InputIntent.SetGender(gender)) },
                                role = Role.RadioButton,
                            )
                            .padding(horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = (gender == state.gender),
                            onClick = null,
                        )
                        Text(
                            text = gender.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp),
                        )
                    }
                }
            }
            Button(modifier = Modifier.constrainAs(saveReference) {
                top.linkTo(genderReference.bottom)
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                bottom.linkTo(parent.bottom)
                verticalBias = 0.5f
            }, onClick = {
                viewModel.executeIntent(InputIntent.AddUser)
            }) {
                Text(text = "Save")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInputScreen() {
    InputScreen(navController = NavHostController(LocalContext.current))
}