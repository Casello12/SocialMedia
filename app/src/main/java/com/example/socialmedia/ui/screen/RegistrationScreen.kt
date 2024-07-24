package com.example.socialmedia.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
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
            onDismissRequest = { /* No action needed */ },
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
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Register",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 21.sp
            )
            // Input field for username
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Input field for password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
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