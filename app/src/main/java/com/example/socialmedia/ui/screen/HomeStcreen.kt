package com.example.socialmedia.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.socialmedia.MyApplication
import com.example.socialmedia.data.MockData
import com.example.socialmedia.mydata.Post
import com.example.socialmedia.ui.components.PostsView
import com.example.socialmedia.ui.theme.RedColor
import com.example.socialmedia.ui.viewmodel.PostViewModel
import com.example.socialmedia.ui.viewmodel.PostViewModelFactory

@Composable
fun HomeScreen(sharedViewModel: SharedViewModel = viewModel()) {
    val username by sharedViewModel.username.observeAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Selamat Datang, $username!")
        //PostsView(MockData.posts)
        // Other content
    }
    // Get the application context
    val context = LocalContext.current
    // Access the PostRepository from MyApplication
    val app = context.applicationContext as MyApplication
    // Create the PostViewModel using the factory
    val postViewModel: PostViewModel = viewModel(factory = PostViewModelFactory(app.postRepository))
    // Observe posts from the ViewModel as LiveData
    val posts by postViewModel.allPosts.observeAsState(initial = emptyList())

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Posts",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            posts.forEach { post ->
                PostCard(post)
            }
        }
    }
}

@Composable
fun PostCard(post: Post) {
    var liked by remember { mutableStateOf(false) }
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
                text = post.username,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = post.content,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row{
                IconButton(onClick = { liked = !liked }) {
                    Icon(
                        if (liked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = if (liked) RedColor else Color.Gray
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = {}) {
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