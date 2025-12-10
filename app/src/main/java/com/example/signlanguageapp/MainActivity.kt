package com.example.signlanguageapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.signlanguageapp.screens.LearnScreen
import com.example.signlanguageapp.screens.QuizScreen
import com.example.signlanguageapp.screens.ProgressScreen
import androidx.compose.material3.Typography
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.signlanguageapp.data.ModelHelper
import com.example.signlanguageapp.screens.GameActivity
import com.example.signlanguageapp.screens.HomeScreen
import com.example.signlanguageapp.screens.RegisterScreen
import com.example.signlanguageapp.screens.SignUpScreen
import com.example.signlanguageapp.screens.SelectionScreen
import com.example.signlanguageapp.ui.theme.AppTheme
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.signlanguageapp.screens.GameScreen


@Composable
fun rememberAppTheme(): MutableState<AppTheme> {
    return rememberSaveable { mutableStateOf(AppTheme.LIGHT) }
}

private val AppTypography = Typography()
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Load YOLOv model and labels
        ModelHelper.loadModel(assets, "yolov8_sign_language_model_float32.tflite") 
        ModelHelper.loadLabels(assets, "label.txt")             

        setContent {
            val appThemeState = rememberSaveable { mutableStateOf(AppTheme.LIGHT) }

            SignLanguageAppTheme(theme = appThemeState.value) {
                MyApp(
                    appTheme = appThemeState.value,
                    onToggleTheme = {
                        appThemeState.value =
                            if (appThemeState.value == AppTheme.LIGHT) AppTheme.DARK else AppTheme.LIGHT
                    }
                )
            }

        }
    }
}

@Composable
fun MyApp(appTheme: AppTheme, onToggleTheme: () -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("signup") { SignUpScreen(navController,appTheme, onToggleTheme) }
        composable("game") { GameScreen(navController,appTheme, onToggleTheme) }
        composable("register") { RegisterScreen(navController,appTheme, onToggleTheme) }
        composable("selection") { SelectionScreen(navController,appTheme, onToggleTheme) }
        composable("translational") { SelectionScreen(navController,appTheme, onToggleTheme) }
        composable("learning") { SelectionScreen(navController,appTheme, onToggleTheme) }
        composable("home") { HomeScreen(navController, appTheme, onToggleTheme) }
        composable("learn") { LearnScreen(navController, appTheme, onToggleTheme) }
        composable("quiz") { QuizScreen(navController, appTheme, onToggleTheme) }
        composable("progress") { ProgressScreen(navController, appTheme, onToggleTheme) }

    }
}


@Composable
fun AnimatedSectionButton(
    text: String,
    icon: ImageVector,
    section: String,
    clickedSection: String,
    color: Color = Color(0xFF99BBEF),
    onClick: () -> Unit
) {
    val backgroundColor by remember { mutableStateOf(Color.Transparent) }
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(30.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SignLanguageAppTheme(theme: AppTheme, content: @Composable () -> Unit)
{
    val colors = if (theme == AppTheme.DARK) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }


    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )

}


@Composable
fun SplashScreen(navController: NavController) {
    var isVisible by remember { mutableStateOf(false) }
    val shimmerBrush = rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(Unit) {
        isVisible = true
        delay(2500) // Keep "AWAAJ" visible for 2.5 seconds
        navController.navigate("signup") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 2000))
        ) {
            Text(
                text = "AWAAJ",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.3f),
                            Color.White,
                            Color.White.copy(alpha = 0.3f)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(shimmerBrush.value * 500f, shimmerBrush.value * 100f)
                    )
                )
            )
        }
    }
}







