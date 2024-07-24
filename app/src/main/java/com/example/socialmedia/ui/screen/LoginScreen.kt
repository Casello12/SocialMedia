package com.example.socialmedia.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.socialmedia.MyApplication
import com.example.socialmedia.R

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginButtonClicked: (String) -> Unit,
    onRegisterButtonClicked: () -> Unit,
    loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory((LocalContext.current.applicationContext as MyApplication).repository)
    )
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

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
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val eyeIcon: Painter = if (passwordVisible) {
                        painterResource(id = R.drawable.ic_eye_open) // Add your own icon
                    } else {
                        painterResource(id = R.drawable.ic_eye_closed) // Add your own icon
                    }
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(painter = eyeIcon, contentDescription = if (passwordVisible) "Hide password" else "Show password")
                    }
                },
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Display error message if any
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = androidx.compose.ui.graphics.Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Row for Login and Register buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        if (username.isNotEmpty() && password.isNotEmpty()) {
                            loginViewModel.getUser(username, password) { user ->
                                if (user == null) {
                                    errorMessage = "Username Atau Password Salah"
                                } else {
                                    onLoginButtonClicked(username) // If user exists, pass the username to MainScreen
                                }
                            }
                        } else {
                            errorMessage = "Username and password Tidak Boleh Kosong."
                        }
                    }
                ) {
                    Text(text = "Login")
                }
                Button(
                    onClick = {
                        onRegisterButtonClicked()
                    }
                ) {
                    Text(text = "Register")
                }
            }
        }
    }
}

// untuk Preview nya
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(onLoginButtonClicked = {}, onRegisterButtonClicked = {})
}
