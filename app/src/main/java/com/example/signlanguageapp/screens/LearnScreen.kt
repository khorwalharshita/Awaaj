package com.example.signlanguageapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.signlanguageapp.R
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.graphicsLayer
import com.example.signlanguageapp.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LearnScreen(
    navController: NavController,
    appTheme: AppTheme,
    onToggleTheme: () -> Unit
) {

    val signs = listOf("A", "B", "C", "D", "E") // Sign letters available
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Learn Sign Language",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        // Display sign cards in a scrollable column
        LazyColumn {
            items(signs) { letter ->
                SignCard(letter)
            }
        }
    }
}

@Composable
fun SignCard(letter: String) {
    val imageId = when (letter) {
        "A" -> R.drawable.sign_a
        "B" -> R.drawable.sign_b
        "C" -> R.drawable.sign_c
        "D" -> R.drawable.sign_d
        "E" -> R.drawable.sign_e
        else -> R.drawable.ic_launcher_foreground
    }

    var scale by remember { mutableStateOf(1f) } // FIX: Define scale properly
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale) // Scale Effect
            .clickable {
                coroutineScope.launch {
                    scale = 0.95f
                    delay(100)
                    scale = 1f
                }
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "Sign for $letter",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Sign for '$letter'",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
