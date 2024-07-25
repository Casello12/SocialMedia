package com.example.socialmedia.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.socialmedia.MyApplication
import com.example.socialmedia.ui.viewmodel.FollowViewModel
import com.example.socialmedia.ui.viewmodel.FollowViewModelFactory
import com.example.socialmedia.ui.viewmodel.UserViewModel
import com.example.socialmedia.ui.viewmodel.UserViewModelFactory

@Composable
fun ProfileScreenOtherUser(
    username: String,
    sharedViewModel: SharedViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory((LocalContext.current.applicationContext as MyApplication).userRepository)
    ),
    followViewModel: FollowViewModel = viewModel(
        factory = FollowViewModelFactory((LocalContext.current.applicationContext as MyApplication).followRepository)
    )
) {
    val currentUserId by sharedViewModel.userId.observeAsState(0)
    val user by userViewModel.getUserByUsername(username).observeAsState()
    var isFollowing by remember { mutableStateOf(false) } // This should be determined by your follow relationship logic

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

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        // Implement the follow/unfollow logic here
                        isFollowing = !isFollowing
                        if (isFollowing) {
                            // Add follow relationship in the database
                            followViewModel.insertFollow(currentUserId, it.id)
                        } else {
                            // Remove follow relationship in the database
                            followViewModel.deleteFollow(currentUserId, it.id)
                        }
                    }
                ) {
                    Text(if (isFollowing) "Unfollow" else "Follow")
                }
            } ?: Text("Loading...")
        }
    }
}
