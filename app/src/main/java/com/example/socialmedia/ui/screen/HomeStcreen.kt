package com.example.socialmedia.ui.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.socialmedia.MyApplication
import com.example.socialmedia.mydata.Post
import com.example.socialmedia.ui.theme.RedColor
import com.example.socialmedia.ui.viewmodel.PostViewModel
import com.example.socialmedia.ui.viewmodel.PostViewModelFactory

@Composable
fun HomeScreen(navController: NavController, sharedViewModel: SharedViewModel = viewModel()) {
    val username by sharedViewModel.username.observeAsState("")
    val context = LocalContext.current
    val app = context.applicationContext as MyApplication
    val postViewModel: PostViewModel = viewModel(factory = PostViewModelFactory(app.postRepository))
    val posts by postViewModel.allPosts.observeAsState(initial = emptyList())

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
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(posts.size) { index ->
                    username?.let {
                        PostCard(post = posts[index], postViewModel = postViewModel, navController = navController, username = it)
                    }
                }
            }
        }
    }
}

@Composable
fun PostCard(post: Post, postViewModel: PostViewModel, navController: NavController, username: String) {
    var liked by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(post.id) {
        liked = postViewModel.isPostLiked(post.id, username)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "username: " + post.username,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clickable {
                        navController.navigate("profileotheruser/${post.username}")
                    }
            )
            Text(
                text = post.content,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        liked = !liked
                        postViewModel.toggleLike(post.id, username, liked)
                    }
                ) {
                    Icon(
                        if (liked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = if (liked) RedColor else Color.Gray
                    )
                }
                Spacer(modifier = Modifier.weight(1f)) // Pushes the share button to the end
                IconButton(
                    onClick = { shareContent(context, post.content) }
                ) {
                    Icon(
                        Icons.Outlined.Send,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

private fun shareContent(context: Context, content: String) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, content)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
}

