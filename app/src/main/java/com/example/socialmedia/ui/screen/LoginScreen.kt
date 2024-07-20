package com.example.socialmedia.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.socialmedia.MyApplication
import androidx.compose.ui.graphics.Color

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginButtonClicked: () -> Unit,
    loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory((LocalContext.current.applicationContext as MyApplication).repository)
    )
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var showError by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Login",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 21.sp
            )

            // Input fields for username and password
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Tampilkan Error Jika Field Kosong
            if (showError) {
                Text(
                    text = "Username and Password cannot be empty",
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Login button
            Button(
                onClick = {
                    if (username.isNotEmpty() && password.isNotEmpty()) {
                        loginViewModel.insertUser(username, password)
                        onLoginButtonClicked()
                    } else {
                        showError = true
                    }
                }
            ) {
                Text(text = "Login")
            }
        }
    }
}

// Preview function
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(onLoginButtonClicked = {})
}
