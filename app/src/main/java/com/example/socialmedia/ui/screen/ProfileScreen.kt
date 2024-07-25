package com.example.socialmedia.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.socialmedia.MyApplication
import com.example.socialmedia.R
import com.example.socialmedia.ui.viewmodel.UserViewModel
import com.example.socialmedia.ui.viewmodel.UserViewModelFactory

@Composable
fun ProfileScreen(
    sharedViewModel: SharedViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory((LocalContext.current.applicationContext as MyApplication).userRepository)
    ) // Get instance of UserViewModel
) {
    val username by sharedViewModel.username.observeAsState()
    val imageUri by sharedViewModel.imageUri.observeAsState()
    var newPassword by remember { mutableStateOf("") }
    var newImageUri by rememberSaveable { mutableStateOf<String?>(imageUri) }

    var showAlert by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            newImageUri = it.toString()
            sharedViewModel.setImageUri(it.toString())
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

            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier.size(128.dp)
            ) {
                if (newImageUri != null) {
                    Image(
                        painter = rememberImagePainter(
                            data = newImageUri,
                            builder = {
                                placeholder(R.drawable.placeholder_user) // assuming you have a placeholder drawable
                                error(R.drawable.error) // assuming you have an error drawable
                            }),
                        contentDescription = "Profile Image",
                        modifier = Modifier.size(128.dp)
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_person_24),
                            contentDescription = "Select Image",
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )
                        Text("Select Image")
                    }
                }
            }

            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("New Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (newPassword.isEmpty()) {
                        alertMessage = "Password cannot be empty."
                        showAlert = true
                    } else {
                        userViewModel.updatePassword(username ?: "", newPassword)
                        newImageUri?.let { userViewModel.updateImageUri(username ?: "", it) }
                        showSuccessDialog = true
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Update Profile")
            }

            Text(
                text = "Username: ${username ?: "Loading..."}",
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }

    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            confirmButton = {
                TextButton(onClick = { showAlert = false }) {
                    Text("OK")
                }
            },
            title = { Text("Error") },
            text = { Text(alertMessage) }
        )
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            confirmButton = {
                TextButton(onClick = { showSuccessDialog = false }) {
                    Text("OK")
                }
            },
            title = { Text("Success") },
            text = { Text("Profile updated successfully.") }
        )
    }
}
