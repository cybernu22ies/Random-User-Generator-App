package com.example.randomuser.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.randomuser.R
import com.example.randomuser.domain.ApiResponse
import com.example.randomuser.ui.theme.RandomUserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel by viewModels<RandomUserViewModel>()

        setContent {
            RandomUserTheme {
                Surface {
                    RandomUserApp(viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomUserApp(
    viewModel: RandomUserViewModel
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val allUsersListState = rememberLazyListState()
    val selectedUser by viewModel.selectedUser.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.requestedUser) {
        if (
            viewModel.requestedUser is ApiResponse.Success<*> &&
            navController.currentDestination?.route == AppDestinations.USER_GENERATOR_SCREEN
        ) {
            navController.navigate(AppDestinations.USER_DETAIL_SCREEN)
        }

        if (viewModel.requestedUser is ApiResponse.Error) {
            snackbarHostState.showSnackbar((viewModel.requestedUser as ApiResponse.Error<*>).message)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.padding(bottom = 48.dp),
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestinations.USER_GENERATOR_SCREEN
        ) {
            composable(AppDestinations.USER_GENERATOR_SCREEN) {
                UserGeneratorScreen(
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    viewModel.requestedUser,
                    stringArrayResource(R.array.genders),
                    mapOf(
                        *stringArrayResource(R.array.nats)
                            .zip(stringArrayResource(R.array.nationalities))
                            .toTypedArray(),
                    ),
                    onUserGeneration = { gender, nationality ->
                        viewModel.requestRandomUser(gender, nationality)
                    },
                    onNavigateBack = {
                        navController.navigate(AppDestinations.ALL_USERS_SCREEN) {
                            popUpTo(AppDestinations.USER_GENERATOR_SCREEN) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(AppDestinations.USER_DETAIL_SCREEN) {
                selectedUser?.let {
                    UserDetailsScreen(
                        modifier = Modifier.padding(innerPadding),
                        user = it,
                        onNavigateBack = {
                            navController.navigate(AppDestinations.ALL_USERS_SCREEN) {
                                popUpTo(AppDestinations.USER_DETAIL_SCREEN) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
            composable(AppDestinations.ALL_USERS_SCREEN) {
                AllUsersScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    users = viewModel.users.collectAsStateWithLifecycle().value,
                    onUserCardClicked = { user ->
                        viewModel.onUserSelected(user)
                        navController.navigate(AppDestinations.USER_DETAIL_SCREEN) {
                            launchSingleTop = true
                        }
                    },
                    usersListState = allUsersListState,
                    onAddUserButtonClicked = {
                        navController.navigate(AppDestinations.USER_GENERATOR_SCREEN) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

object AppDestinations {
    const val USER_GENERATOR_SCREEN = "UserGenerator"
    const val USER_DETAIL_SCREEN = "UserDetail"
    const val ALL_USERS_SCREEN = "AllUsers"
}
