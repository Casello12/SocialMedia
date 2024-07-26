package com.example.socialmedia.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.socialmedia.MyApplication
import com.example.socialmedia.mydata.Like
import com.example.socialmedia.mydata.LikeWithContent
import com.example.socialmedia.mydata.Post
import com.example.socialmedia.ui.theme.RedColor
import com.example.socialmedia.ui.viewmodel.LikeViewModel
import com.example.socialmedia.ui.viewmodel.LikeViewModelFactory
import com.example.socialmedia.ui.viewmodel.PostViewModel
import com.example.socialmedia.ui.viewmodel.PostViewModelFactory

@Composable
fun ActivitiesScreen(navController: NavController, sharedViewModel: SharedViewModel = viewModel()) {
    val username by sharedViewModel.username.observeAsState()
    val context = LocalContext.current
    val app = context.applicationContext as MyApplication
    val likeViewModel: LikeViewModel = viewModel(factory = LikeViewModelFactory(app.likeRepository))
    val likesWithContent by likeViewModel.allLikesWithContent.observeAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.LightGray, Color.LightGray)
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {


            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp) // Add padding to the bottom of the LazyColumn
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp), // Add padding to separate rows
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween // Align items to the start and end
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
                            text = "Like Page",
                            fontSize = 24.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                }
                items(likesWithContent.size) { index ->
                    LikeRow(likeWithContent = likesWithContent[index])
                }
            }
        }

    }
}

@Composable
fun LikeRow(likeWithContent: LikeWithContent) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "username: " + likeWithContent.username,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = likeWithContent.content,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Like",
                    modifier = Modifier.size(28.dp),
                    tint = RedColor
                )

            }
        }
    }
}


