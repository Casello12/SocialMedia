package com.example.socialmedia.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.socialmedia.ui.theme.Purple40
import com.example.socialmedia.ui.theme.RedColor

@Composable
fun TopAppView(navController: NavHostController) {
    Column(Modifier.padding(10.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Social Media UNDIRA",
                fontFamily = FontFamily.SansSerif,
                color = if (isSystemInDarkTheme()) Color.White else Purple40,
                modifier = Modifier.weight(1f),
                fontSize = 21.sp
            )
            IconButton(onClick = {}) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    Icon(Icons.Filled.Send, contentDescription = "Messages")
                    Card(
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.size(14.dp)
                    ) {
                        Box(
                            modifier = Modifier.background(RedColor).fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "+9",
                                color = Color.White,
                                fontSize = 8.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }


        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = { navController.navigate("loginscreen") }) {
                Text(text = "LogOut")
            }
//            TextButton(onClick = { navController.navigate("registerScreen") }) {
//                Text(text = "Register")
//            }


        }
    }




}