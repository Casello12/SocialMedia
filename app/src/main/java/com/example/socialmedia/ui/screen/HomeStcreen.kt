package com.example.socialmedia.ui.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.socialmedia.MyApplication
import com.example.socialmedia.mydata.Post
import com.example.socialmedia.ui.theme.RedColor
import com.example.socialmedia.ui.viewmodel.PostViewModel
import com.example.socialmedia.ui.viewmodel.PostViewModelFactory

@Composable
fun HomeScreen(sharedViewModel: SharedViewModel = viewModel()) {
    val username by sharedViewModel.username.observeAsState("")
    val context = LocalContext.current
    val app = context.applicationContext as MyApplication
    val postViewModel: PostViewModel = viewModel(factory = PostViewModelFactory(app.postRepository))
    val posts by postViewModel.allPosts.observeAsState(initial = emptyList())

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
            items(posts.size) { index ->
                username?.let {
                    PostCard(post = posts[index], postViewModel = postViewModel, it) }
            }
        }
    }
}

@Composable
fun PostCard(post: Post, postViewModel: PostViewModel, username: String) {
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
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "username: " + post.username,
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
            Row {
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
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {shareContent(context, post.content)}
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
