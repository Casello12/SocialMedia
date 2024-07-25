package com.example.socialmedia.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.socialmedia.MyApplication

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    onRegisterButtonClicked: () -> Unit,
    loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory((LocalContext.current.applicationContext as MyApplication).repository)
    )
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var isRegistrationSuccessful by remember { mutableStateOf(false) }

    // Handle showing the dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Info") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        if (isRegistrationSuccessful) {
                            onRegisterButtonClicked()
                        }
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Blue, Color.Magenta)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
                .align(Alignment.Center)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Register",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 21.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            // Input field for username
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", style = TextStyle(fontSize = 16.sp, color = Color.Black)) },
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Input field for password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", style = TextStyle(fontSize = 16.sp, color = Color.Black)) },
                textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Display error message if any
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = androidx.compose.ui.graphics.Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Register button
            Button(
                onClick = {
                    if (username.isNotEmpty() && password.isNotEmpty()) {
                        loginViewModel.getUserByUsername(username) { user ->
                            if (user == null) {
                                loginViewModel.insertUser(username, password)
                                errorMessage = "Berhasil Register Username"
                                showDialog = true
                                isRegistrationSuccessful = true
                            } else {
                                errorMessage = "nama username sudah ada"
                                showDialog = true
                                isRegistrationSuccessful = false
                            }
                        }
                    } else {
                        errorMessage = "Username and password Tidak Boleh Kosong."
                        isRegistrationSuccessful = false
                        showDialog = true
                    }
                }
            ) {
                Text(text = "Register")
            }
        }
    }
}