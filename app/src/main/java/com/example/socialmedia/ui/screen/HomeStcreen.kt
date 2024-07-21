package com.example.socialmedia.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.socialmedia.data.MockData
import com.example.socialmedia.ui.components.PostsView

@Composable
fun HomeScreen(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val username by sharedViewModel.username.observeAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Selamat Datang, $username!")
        PostsView(MockData.posts)
        // Other content
    }
}