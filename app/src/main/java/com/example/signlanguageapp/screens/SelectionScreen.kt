package com.example.signlanguageapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.signlanguageapp.R
import kotlinx.coroutines.delay

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.signlanguageapp.ui.theme.AppTheme
import com.example.signlanguageapp.ui.theme.ThemeToggleIcon
import com.example.signlanguageapp.ui.theme.ThemedBackground
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalDensity

import androidx.compose.animation.core.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.launch

@Composable
fun SelectionScreen(
    navController: NavController,
    appTheme: AppTheme,
    onToggleTheme: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ThemedBackground(theme = appTheme) {
            ThemeToggleIcon(theme = appTheme, onToggleTheme = onToggleTheme)

            // Animation for sliding down with elasticity
            val slideAnimation = remember { Animatable(0f) }
            val scaleAnimation = remember { Animatable(0.5f) }

            // Use LaunchedEffect to trigger the animation with a delay
            LaunchedEffect(Unit) {
                // Add a delay before starting the animation
                delay(1500) // 1500ms delay (adjust as needed)

                launch {
                    // Slide down with spring animation (elastic bounce effect)
                    slideAnimation.animateTo(
                        targetValue = 100f, // Final slide position (100dp from the top)
                        animationSpec = spring(
                            dampingRatio = 0.5f, // Bounce
                            stiffness = Spring.StiffnessMedium // Medium elasticity
                        )
                    )
                }

                launch {
                    // Scale up with a smooth effect
                    scaleAnimation.animateTo(
                        targetValue = 1f, // End at full scale
                        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                    )
                }
            }

            // Centered Column for text content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp), // Padding for the column
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val isDark = isSystemInDarkTheme()

                val gradientColors = if (isDark) {
                    // Light gradient for dark theme
                    listOf(Color.White, Color(0xFFBFA6FF), Color.White)
                } else {
                    // Darker gradient for light theme
                    listOf(Color(0xFF0A0A0A), Color(0xFF4444B7), Color(0xFF0A0A0A))
                }

                // Title text with animation (MODULE text like AWAAJ)
                Text(
                    text = "MODULE",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        brush = Brush.linearGradient(colors = gradientColors)
                    ),
                    modifier = Modifier
                        .padding(top = with(LocalDensity.current) { slideAnimation.value.toDp() })
                        .graphicsLayer(
                            scaleX = scaleAnimation.value,
                            scaleY = scaleAnimation.value
                        )
                )

                // Subtitle text
                Text(
                    text = "Because Every Hand Has a Voice.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(top = 4.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )

                // Example GameCard components can go here
                Spacer(modifier = Modifier.height(24.dp))

                // Example GameCard
                GameCard(
                    imageResId = R.drawable.translate_symbol,
                    title = "Translational",
                    onClick = { navController.navigate("home") }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Another GameCard
                GameCard(
                    imageResId = R.drawable.learn_symbol,
                    title = "Learning",
                    onClick = { navController.navigate("home") }
                )
            }
        }
    }
}


    @Composable
fun GameCard(
    imageResId: Int,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            // Static Image
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = title,
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 8.dp)
            )

            // Title
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


@Composable
fun AnimatedGameCard(
    imageResId: Int,
    title: String,
    delayMillis: Int = 0,
    onClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    // Trigger visibility with delay
    LaunchedEffect(Unit) {
        delay(delayMillis.toLong())
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) + fadeIn(),
    ) {
        GameCard(
            imageResId = imageResId,
            title = title,
            onClick = onClick
        )
    }
}





