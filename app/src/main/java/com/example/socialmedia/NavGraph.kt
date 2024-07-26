package com.example.socialmedia


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.socialmedia.ui.components.BottomBarView
import com.example.socialmedia.ui.components.TopAppView
import com.example.socialmedia.ui.screen.*
import com.example.socialmedia.ui.screen.SharedViewModel
import com.example.socialmedia.ui.utils.NavigationItem

@Composable
fun NavGraph(
    startDestination: String = "loginscreen",
    navController: NavHostController,
    sharedViewModel: SharedViewModel = viewModel()
) {
    var fullScreen by remember { mutableStateOf(false) }

    val username by sharedViewModel.username.observeAsState()

    Scaffold(
        topBar = {
            if (!fullScreen) TopAppView(navController, username)
        },
        bottomBar = {
            if (!fullScreen) BottomBarView(navController)
        }
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = startDestination
        ) {
            composable("home") {
                fullScreen = false
                HomeScreen(navController, sharedViewModel)
            }
            composable("following") {
                fullScreen = false
                FollowingScreen(navController, sharedViewModel)
            }
            composable("add") {
                fullScreen = false
                AddPostScreen(sharedViewModel, navController)
            }
            composable("activities") {
                fullScreen = false
                ActivitiesScreen(navController, sharedViewModel)
            }
            composable("profile") {
                fullScreen = false
                ProfileScreen(sharedViewModel)
            }
            composable(
                route = "profileotheruser/{username}",
                arguments = listOf(navArgument("username") { type = NavType.StringType })
            ) { entry ->
                fullScreen = false
                val profileUsername = entry.arguments?.getString("username")
                ProfileScreenOtherUser(profileUsername ?: "", sharedViewModel)
            }
            composable("registerScreen") {
                fullScreen = true
                RegistrationScreen(
                    onRegisterButtonClicked = {
                        navController.navigate("loginscreen") {
                            popUpTo("registerScreen") { inclusive = true }
                        }
                    }
                )
            }
            composable("loginscreen") {
                fullScreen = true
                LoginScreen(
                    onLoginButtonClicked = { username ->
                        sharedViewModel.setUsername(username)
                        navController.navigate("home") {
                            popUpTo("loginscreen") { inclusive = true }
                        }
                    },
                    onRegisterButtonClicked = {
                        navController.navigate("registerScreen")
                    }
                )
            }

        }
    }
}
