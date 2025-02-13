package com.example.socialmedia.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.socialmedia.MyApplication
import com.example.socialmedia.mydata.User
import com.example.socialmedia.ui.viewmodel.FollowViewModel
import com.example.socialmedia.ui.viewmodel.FollowViewModelFactory
import com.example.socialmedia.ui.viewmodel.UserViewModel
import com.example.socialmedia.ui.viewmodel.UserViewModelFactory

@Composable
fun FollowingScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory((LocalContext.current.applicationContext as MyApplication).userRepository)
    ),
    followViewModel: FollowViewModel = viewModel(
        factory = FollowViewModelFactory((LocalContext.current.applicationContext as MyApplication).followRepository)
    )
) {
    val username by sharedViewModel.username.observeAsState()
    var userId by remember { mutableStateOf<Int?>(null) }
    val followings by followViewModel.getFollowingsByUserId(userId ?: 0).observeAsState(emptyList())
    var followingUsers by remember { mutableStateOf<List<User>>(emptyList()) }

    LaunchedEffect(username) {
        username?.let {
            userViewModel.getUserIdByUsername(it) { id ->
                userId = id
                id?.let {
                    followViewModel.getFollowingsByUserId(it).observeForever { followings ->
                        val followingIds = followings.map { it.followeeId }
                        userViewModel.getUsersByIds(followingIds) { users ->
                            followingUsers = users
                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.LightGray, Color.LightGray)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        if (followingUsers.isEmpty()) {
            Text(text = "No followings for $username")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Welcome, $username",
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 21.sp,
                            color = Color.Black
                        )
                    }
                }
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Kamu Sudah Follow",
                            fontSize = 24.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
                items(followingUsers) { user ->
                    FollowingItem(user = user, navController = navController)
                }
            }
        }
    }
}

@Composable
fun FollowingItem(user: User, navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Username: ${user.username}")
            user.imageUri?.let {
                coil.compose.AsyncImage(
                    model = it,
                    contentDescription = "Profile Image",
                    modifier = Modifier.size(64.dp).padding(top = 8.dp)
                )
            }
            Button(
                onClick = {
                    // Navigate to the direct message screen with the user's username
                    navController.navigate("directMessage/${user.username}")
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = "Direct Message")
            }
        }
    }
}