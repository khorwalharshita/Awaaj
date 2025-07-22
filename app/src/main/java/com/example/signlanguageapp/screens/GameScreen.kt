package com.example.signlanguageapp.screens
import android.os.CountDownTimer
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.signlanguageapp.R
import com.example.signlanguageapp.ui.theme.AppTheme
import com.example.signlanguageapp.ui.theme.ThemeToggleIcon
import com.example.signlanguageapp.ui.theme.ThemedBackground


@Composable
fun GameScreen(navController: NavController,
               appTheme: AppTheme,
               onToggleTheme: () -> Unit) {
    ThemedBackground(theme = appTheme) {
        ThemeToggleIcon(theme = appTheme, onToggleTheme = onToggleTheme)
    var score by remember { mutableStateOf(0) }
    var lives by remember { mutableStateOf(3) }
    var timeLeft by remember { mutableStateOf(10) }
    var correctIndex by remember { mutableStateOf((0..4).random()) }
    val letters = listOf("A", "B", "C", "N", "H")
    val images = listOf(
        R.drawable.sign_a, R.drawable.sign_b, R.drawable.sign_c,
        R.drawable.sign_n, R.drawable.sign_h

    )

    val context = LocalContext.current
    var timer: CountDownTimer? = remember { null }

    LaunchedEffect(key1 = correctIndex) {
        timer?.cancel()
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                lives -= 1
                if (lives > 0) {
                    correctIndex = (0..4).random()
                } else {
                    Toast.makeText(context, "Game Over!", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = images[correctIndex]),
            contentDescription = "Sign",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Ｓcore💪🏻 : $score",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 20.sp
        )

        Text(
            text = "Ｌives  : $lives",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 20.sp
        )

        Text(
            text = "Ｔime⏳ : $timeLeft s",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 20.sp
        )



        Spacer(modifier = Modifier.height(10.dp))
        val options = remember(correctIndex) {
            val wrong = (letters.indices - correctIndex).shuffled().take(3).toMutableList()
            wrong.add(correctIndex)
            wrong.shuffled()
        }

        options.forEach { index ->
            Button(onClick = {
                if (index == correctIndex) {
                    score++
                    Toast.makeText(context, "\uD83E\uDD29 Correct!", Toast.LENGTH_SHORT).show()
                } else {
                    lives--
                    Toast.makeText(context, "\uD83D\uDE2C Wrong!", Toast.LENGTH_SHORT).show()
                }
                if (lives > 0) {
                    correctIndex = (0..4).random()
                    timeLeft = 10
                } else {
                    Toast.makeText(context, "ＧＡＭＥ　ＯＶＥＲ！: $score", Toast.LENGTH_LONG).show()
                }
            }) {
                Text(text = letters[index])
            }
        }
    }
}
}
