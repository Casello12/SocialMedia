package com.example.socialmedia.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Blue, Color.Magenta)
                )
            )
    ) {
        // Use `Modifier.padding` to ensure there is padding around the content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp)
                .align(Alignment.Center)
                .wrapContentSize() // Ensures that the content sizes itself correctly
        ) {
            Text(
                text = "Social Media Login",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp),
                fontSize = 21.sp
            )

            // Input fields for username and password
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
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
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
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
                    },
                    modifier = Modifier.weight(1f) // Button takes up available width equally
                ) {
                    Text(text = "Login")
                }

                TextButton(
                    onClick = {
                        onRegisterButtonClicked()
                    },
                    modifier = Modifier.weight(1f) // Button takes up available width equally
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

