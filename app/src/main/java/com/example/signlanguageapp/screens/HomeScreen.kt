package com.example.signlanguageapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.signlanguageapp.AnimatedSectionButton
import com.example.signlanguageapp.ui.theme.AppTheme
import com.example.signlanguageapp.ui.theme.ThemedBackground

@Composable
fun HomeScreen(
    navController: NavController,
    appTheme: AppTheme,
    onToggleTheme: () -> Unit
) {
    var clickedSection by remember { mutableStateOf("") }

    ThemedBackground(theme = appTheme) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val isDark = isSystemInDarkTheme()

            val gradientColors = if (isDark) {
                // Light gradient for dark theme
                listOf(Color(0xFF0A0A0A), Color(0xFF4444B7), Color(0xFF0A0A0A))
            } else {
                // Darker gradient for light theme
                listOf(Color.White, Color(0xFF7056B6), Color.White)

            }
            Text(
                text = "AWAAJ",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Serif,
                color = Color(0xFF302C3B),
                style = TextStyle(
                    brush = Brush.linearGradient(colors = gradientColors)
                ),
                modifier = Modifier.padding(top = 120.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Where Hands Find Voice",
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(120.dp))

            // Buttons
            AnimatedSectionButton(
                text = "Learn",
                icon = Icons.Filled.Menu,
                section = "learn",
                clickedSection = clickedSection,
                onClick = {
                    clickedSection = "learn"
                    navController.navigate("learn")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            AnimatedSectionButton(
                text = "Quiz",
                icon = Icons.Filled.QuestionMark,
                section = "quiz",
                clickedSection = clickedSection,
                onClick = {
                    clickedSection = "quiz"
                    navController.navigate("quiz")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            AnimatedSectionButton(
                text = "Progress",
                icon = Icons.Filled.Assessment,
                section = "progress",
                clickedSection = clickedSection,
                onClick = {
                    clickedSection = "progress"
                    navController.navigate("progress")
                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Theme toggle icon
            IconButton(onClick = onToggleTheme) {
                Icon(
                    imageVector = if (appTheme == AppTheme.DARK) Icons.Filled.WbSunny else Icons.Filled.NightlightRound,
                    contentDescription = "Toggle Theme",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
