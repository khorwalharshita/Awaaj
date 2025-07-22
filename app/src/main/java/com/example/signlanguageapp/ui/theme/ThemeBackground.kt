package com.example.signlanguageapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.signlanguageapp.R

enum class AppTheme {
    LIGHT, DARK
}


@Composable
fun ThemedBackground(
    theme: AppTheme,
    content: @Composable BoxScope.() -> Unit
) {
    val backgroundImage = when (theme) {
        AppTheme.LIGHT -> R.drawable.lighttheme  // your light bg
        AppTheme.DARK -> R.drawable.darktheme     // your dark bg
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        content()
    }
}

