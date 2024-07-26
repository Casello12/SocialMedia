package com.example.socialmedia.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.socialmedia.MyApplication
import com.example.socialmedia.mydata.DirectMessage
import com.example.socialmedia.ui.viewmodel.DirectMessageViewModel
import com.example.socialmedia.ui.viewmodel.DirectMessageViewModelFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DirectMessageScreen(
    usernameWith: String,
    sharedViewModel: SharedViewModel = viewModel(),
    navController: NavController,
    directMessageViewModel: DirectMessageViewModel = viewModel(
        factory = DirectMessageViewModelFactory((LocalContext.current.applicationContext as MyApplication).directMessageRepository)
    )
) {
    val myUsername by sharedViewModel.username.observeAsState("")
    val messages by directMessageViewModel.getMessagesBetweenUsers(usernameWith, myUsername ?: "").observeAsState(emptyList())

    var newMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            item {
                Text(
                    text = "Direct Message with $usernameWith",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            items(messages) { message ->
                DirectMessageItem(message)
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = newMessage,
                onValueChange = { newMessage = it },
                label = { Text("Type a message") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (newMessage.isNotEmpty()) {
                        val message = DirectMessage(
                            usernameWith = usernameWith,
                            usernameFrom = myUsername ?: "",
                            contentMessage = newMessage,
                            timestamp = System.currentTimeMillis()
                        )
                        coroutineScope.launch {
                            directMessageViewModel.insertMessage(message)
                            newMessage = ""
                        }
                    }
                }
            ) {
                Text("Send")
            }
        }
    }
}

@Composable
fun DirectMessageItem(message: DirectMessage) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Text(
                text = "${message.usernameFrom}: ${message.contentMessage}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = " ${formatTimestamp(message.timestamp)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss", Locale.getDefault())
    return sdf.format(Date(timestamp))
}