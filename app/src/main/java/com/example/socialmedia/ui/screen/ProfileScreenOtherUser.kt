package com.example.socialmedia.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
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
    val myUsername by sharedViewModel.username.observeAsState()
    var userId by remember { mutableStateOf<Int?>(null) }
    val user by userViewModel.getUserByUsername(username).observeAsState()
    var isFollowing by remember { mutableStateOf(false) }

    LaunchedEffect(myUsername) {
        myUsername?.let {
            userViewModel.getUserIdByUsername(it) { id ->
                userId = id
            }
        }
    }

    LaunchedEffect(user) {
        user?.let {
            userId?.let { id ->
                followViewModel.isFollowing(id, it.id) { following ->
                    isFollowing = following
                }
            }
        }
    }

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

                if (userId != it.id) {
                    Button(
                        onClick = {
                            isFollowing = !isFollowing
                            if (isFollowing) {
                                followViewModel.insertFollow(userId ?: 0, it.id)
                            } else {
                                followViewModel.deleteFollow(userId ?: 0, it.id)
                            }
                        }
                    ) {
                        Text(if (isFollowing) "Unfollow" else "Follow")
                    }
                }
            } ?: Text("Loading...")
        }
    }
}
