package com.example.socialmedia.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.socialmedia.ui.theme.Purple40

@Composable
fun TopAppView(navController: NavHostController, username: String?) {
    Column(Modifier.padding(10.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween // Align items to the start and end
        ) {
            Text(
                text = "Social Media UNDIRA",
                fontFamily = FontFamily.SansSerif,
                color = if (isSystemInDarkTheme()) Color.White else Purple40,
                fontSize = 21.sp
            )
            IconButton(onClick = {}) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    Icon(Icons.Filled.Send, contentDescription = "Messages")
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp), // Add padding to separate rows
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Align items to the start and end
        ) {
            Text(
                text = "Welcome, $username",
                fontFamily = FontFamily.SansSerif,
                fontSize = 21.sp
            )

            OutlinedButton(onClick = { navController.navigate("loginscreen") }) {
                Text(text = "LogOut")
            }
        }
    }
}
