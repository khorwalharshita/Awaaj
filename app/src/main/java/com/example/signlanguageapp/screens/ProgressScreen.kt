package com.example.signlanguageapp.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.signlanguageapp.ui.theme.AppTheme

@Composable
fun ProgressScreen(
    navController: NavController,
    appTheme: AppTheme,
    onToggleTheme: () -> Unit
) {
    var progress by remember { mutableStateOf(50) }  // Assume 50% progress
    val animatedProgress by animateFloatAsState(targetValue = progress / 100f, label = "")

    val message = when {
        progress == 100 -> "Congratulations!🎉 You're a pro!"
        progress >= 75 -> "Almost there! Keep pushing! \uD83D\uDCAA\uD83C\uDFFC"
        progress >= 50 -> "Great job! Keep learning! \uD83D\uDC4D\uD83C\uDFFC"
        progress >= 25 -> "You're getting better! ☺\uFE0F"
        else -> "Just getting started! 🚀"
    }

    val badge = when {
        progress >= 100 -> "🏆 Master of Sign Language"
        progress >= 75 -> "\uD83D\uDCAA\uD83C\uDFFC Advanced Learner"
        progress >= 50 -> "\uD83E\uDEF6\uD83C\uDFFC Intermediate"
        progress >= 25 -> "🌱 Beginner"
        else -> "🚀 Getting Started"
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Your Learning Progress", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(40.dp))

        // Progress Bar with Animation
        LinearProgressIndicator(progress = animatedProgress, modifier = Modifier.fillMaxWidth().height(10.dp))

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "$progress% Completed", fontWeight = FontWeight.Bold)

        Text(text = message, fontWeight = FontWeight.SemiBold, color = Color(0xFF48779B))

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Achievement: $badge", fontWeight = FontWeight.Bold, color =Color(0xFF77C1E3))

        Spacer(modifier = Modifier.height(16.dp))

        // Increase Progress Button
        Button(onClick = { if (progress < 100) progress += 10 }) {
            Text(text = "Practice More")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Reset Progress Button
        Button(
            onClick = { progress = 0 },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF193042))
        ){
            Text(text = "Reset Progress")
            }

    }
}
