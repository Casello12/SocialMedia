package com.example.socialmedia.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter

@Composable
fun ProfileScreen(
    sharedViewModel: SharedViewModel = viewModel()
) {
    val username by sharedViewModel.username.observeAsState()
    var isLoading by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (username != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "User Profile",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                )
                // Load the image from the URL
                AsyncImage(
                    model = "https://place-hold.it/128",
                    contentDescription = "Profile Image",
                    placeholder = rememberImagePainter("https://place-hold.it/128"),
                    onSuccess = { isLoading = false },
                    modifier = Modifier
                        .size(128.dp)
                        .padding(bottom = 16.dp)
                )


                Text(
                    text = "Username: ${username}",
                    fontSize = 24.sp
                )
            }
        } else {
            Text(text = "Loading...")
        }
    }
}
