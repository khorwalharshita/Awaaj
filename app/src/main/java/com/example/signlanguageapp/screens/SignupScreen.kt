package com.example.signlanguageapp.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.signlanguageapp.ui.theme.AppTheme
import com.example.signlanguageapp.ui.theme.ThemeToggleIcon
import com.example.signlanguageapp.ui.theme.ThemedBackground


@Composable
fun SignUpScreen(
    navController: NavController,
    appTheme: AppTheme,
    onToggleTheme: () -> Unit
) {


    Box(modifier = Modifier.fillMaxSize()) {
        ThemedBackground(theme = appTheme) {
            ThemeToggleIcon(theme = appTheme, onToggleTheme = onToggleTheme)

            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "AWAAJ",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF0A0A0A), Color(0xFF4444B7), Color(0xFF0A0A0A))
                        )
                    )
                )

                Text(
                    text = "Hands Tell Stories, We Help You Hear Them.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(top = 4.dp),
                )

                Spacer(modifier = Modifier.height(50.dp))
                Text("Create an account", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Enter your email and start learning with AWAAJ", fontSize = 14.sp)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("email@domain.com") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Enter password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = {
                        navController.navigate("selection")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text(
                        text = "Continue",
                        style = TextStyle(
                            brush = Brush.linearGradient(
                                colors = listOf(Color.Gray, Color.LightGray, Color.Gray)
                            ),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Navigation Link Text
                Row {
                    Text("Don't have an account? ")
                    Text(
                        text = "SIGN UP",
                        color = Color(0xFF4444B7),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navController.navigate("register") // Replace with actual route
                        }
                    )
                    Text(
                        text = "game",
                        color = Color(0xFF4444B7),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navController.navigate("game") // Replace with actual route
                        }
                    )
                }
            }
        }
    }
}