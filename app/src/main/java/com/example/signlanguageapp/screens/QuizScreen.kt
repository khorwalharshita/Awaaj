package com.example.signlanguageapp.screens
import com.example.signlanguageapp.data.ModelHelper
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


import com.example.signlanguageapp.R
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.Locale
import com.example.signlanguageapp.ui.theme.AppTheme
import com.example.signlanguageapp.ui.theme.ThemeToggleIcon
import com.example.signlanguageapp.ui.theme.ThemedBackground

@Composable
fun QuizScreen(
    navController: NavController,
    appTheme: AppTheme,
    onToggleTheme: () -> Unit
) {
    val context = LocalContext.current
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var detectedText by remember { mutableStateOf("Processing...") }
    var voiceInput by remember { mutableStateOf("") }
    val correctAnswer = "B"

    // Image Capture Launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val image = result.data?.extras?.get("data") as? Bitmap
        if (image != null) {
            imageBitmap = image
            processImageWithYolo(image) { prediction ->
                detectedText = prediction
            }
        } else {
            detectedText = "Failed to capture image"
        }
    }

    // Voice Input Launcher
    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val spokenText = result.data
            ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            ?.firstOrNull()

        if (!spokenText.isNullOrEmpty()) {
            voiceInput = spokenText.uppercase(Locale.getDefault())
        }
    }

    // UI Layout
    ThemedBackground(theme = appTheme) {
        ThemeToggleIcon(theme = appTheme, onToggleTheme = onToggleTheme)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Sign Language Quiz", style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    imagePickerLauncher.launch(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("📸 Capture Sign")
            }

            Spacer(Modifier.height(16.dp))

            imageBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Captured Image",
                    modifier = Modifier.size(200.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            Text("Detected from Image: $detectedText")

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                        putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                        )
                        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                        putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak the correct letter")
                    }
                    speechLauncher.launch(intent)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text("🎤 Speak Your Answer", color = Color(0xFFEDF1ED))
            }

            Spacer(Modifier.height(16.dp))

            Text("You said: $voiceInput", style = MaterialTheme.typography.bodyLarge)

            val finalAnswer = detectedText.takeIf { it != "Processing..." && !it.startsWith("Error") }
                ?: voiceInput

            if (finalAnswer.isNotEmpty()) {
                Text(
                    text = if (finalAnswer == correctAnswer) "Correct! 🎉" else "Wrong! ❌",
                    color = if (finalAnswer == correctAnswer) Color.Green else Color.Red,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}
// 🔧 Helper function for processing image using YOLO model
fun processImageWithYolo(bitmap: Bitmap, onResult: (String) -> Unit) {
    try {
        val result = ModelHelper.predict(bitmap)
        val label = result.substringAfter("Prediction: ").substringBefore("\n").trim()
        onResult(label.uppercase())
    } catch (e: Exception) {
        Log.e("QuizScreen", "YOLO model prediction failed", e)
        onResult("Error: ${e.localizedMessage}")
    }
}
