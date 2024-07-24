// AddPostScreen.kt
package com.example.socialmedia.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.socialmedia.MyApplication
import com.example.socialmedia.ui.viewmodel.PostViewModel
import com.example.socialmedia.ui.viewmodel.PostViewModelFactory

@Composable
fun AddPostScreen(sharedViewModel: SharedViewModel = viewModel(), navController: NavController) {

    val username by sharedViewModel.username.observeAsState()
    val context = LocalContext.current
    val app = context.applicationContext as MyApplication
    val postViewModel: PostViewModel = viewModel(factory = PostViewModelFactory(app.postRepository))

    var content by rememberSaveable { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Add Post",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            username?.let {
                Text(
                    text = "Username: $it",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Post Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 16.dp)
            )
            Button(onClick = {
                username?.let {
                    postViewModel.insertPost(it, content)
                    content = ""
                    showDialog = true
                }
            }) {
                Text(text = "Submit")
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Post Submitted") },
                text = { Text("Your post has been successfully submitted!") },
                confirmButton = {
                    Button(onClick = {
                        showDialog = false
                        // Navigate to HomeScreen
                        navController.navigate("home") {
                            popUpTo("add") { inclusive = true }
                        }
                    }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
