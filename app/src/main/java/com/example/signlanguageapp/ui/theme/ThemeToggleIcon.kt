
package com.example.signlanguageapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.ui.unit.dp

@Composable
fun ThemeToggleIcon(
    theme: AppTheme,
    onToggleTheme: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        IconButton(onClick = onToggleTheme) {
            Icon(
                imageVector = if (theme == AppTheme.DARK)
                    Icons.Filled.WbSunny
                else
                    Icons.Filled.NightlightRound,
                contentDescription = "Toggle Theme"
            )
        }
    }
}
