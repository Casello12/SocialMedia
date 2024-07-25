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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.socialmedia.MyApplication
import com.example.socialmedia.ui.viewmodel.UserViewModel
import com.example.socialmedia.ui.viewmodel.UserViewModelFactory

@Composable
fun ProfileScreenOtherUser(
    username: String,
    sharedViewModel: SharedViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory((LocalContext.current.applicationContext as MyApplication).userRepository)
    )
){
    val user by userViewModel.getUserByUsername(username).observeAsState()

    Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "User Profile",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            user?.let {
                Text(
                    text = "Username: ${it.username}",
                    fontSize = 24.sp
                )
                it.imageUri?.let { imageUri ->
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Profile Image",
                        modifier = Modifier.size(128.dp)
                    )
                }
            } ?: Text("Loading...")
        }
    }
}