package com.example.signlanguageapp.ui.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font

import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.GoogleFont.Provider
import com.example.signlanguageapp.R


/**
 * Static font setup (no remember needed).
 * Ideal for using in a global theme.
 */

// Create the Google Font Provider (non-composable version)
private val googleFontProvider = Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs  // <-- this is from your certs file
)

// Font Definitions
private val poppinsFont = GoogleFont("Poppins")
private val montserratFont = GoogleFont("Montserrat")

// Poppins – clean body font
val PoppinsFontFamily = FontFamily(
    Font(googleFont = poppinsFont, fontProvider = googleFontProvider, weight = FontWeight.Normal),
    Font(googleFont = poppinsFont, fontProvider = googleFontProvider, weight = FontWeight.Medium),
    Font(googleFont = poppinsFont, fontProvider = googleFontProvider, weight = FontWeight.Bold),
)

// Montserrat – aesthetic heading font
val MontserratFontFamily = FontFamily(
    Font(googleFont = montserratFont, fontProvider = googleFontProvider, weight = FontWeight.Normal),
    Font(googleFont = montserratFont, fontProvider = googleFontProvider, weight = FontWeight.Medium),
    Font(googleFont = montserratFont, fontProvider = googleFontProvider, weight = FontWeight.Bold),
)
