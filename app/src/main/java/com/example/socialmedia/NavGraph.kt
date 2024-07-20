package com.example.socialmedia

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.socialmedia.ui.components.BottomBarView
import com.example.socialmedia.ui.components.TopAppView
import com.example.socialmedia.ui.screen.*

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    startDestination: String = "loginscreen",
    navController: NavHostController
) {
    var fullScreen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            if (!fullScreen) TopAppView(navController)
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
                HomeScreen(navController)
            }
            composable("search") {
                fullScreen = false
                SearchScreen()
            }
            composable("add") {
                fullScreen = false
                AddPostScreen()
            }
            composable("activities") {
                fullScreen = false
                ActivitiesScreen()
            }
            composable("profile") {
                fullScreen = false
                ProfileScreen()
            }
            composable("registerScreen") {
                fullScreen = true
                RegistrationScreen(
                    onRegisterButtonClicked = {
                        navController.popBackStack()
                    }
                )
            }
            composable("loginscreen") {
                fullScreen = true
                LoginScreen(
                    onLoginButtonClicked = {
                        navController.navigate("home") {
                            popUpTo("loginscreen") { inclusive = true }
                        }
                    }
                )
            }
            composable(
                "showStory/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { entry ->
                fullScreen = true
                ShowStoryScreen(entry.arguments?.getInt("id") ?: 0)
            }
        }
    }
}
