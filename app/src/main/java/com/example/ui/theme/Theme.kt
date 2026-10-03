package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = GoldWarm,               // #E6BB3F
    onPrimary = TealDarkBg,           // #112A31
    primaryContainer = TealSurfaceHigher,
    onPrimaryContainer = GoldCream,   // #F2DB98
    secondary = TealPrimary,          // #318EA7
    onSecondary = Color.White,
    secondaryContainer = TealCardSurface,
    onSecondaryContainer = TealLightAccent,
    tertiary = GoldCream,             // #F2DB98
    onTertiary = TealDarkBg,
    background = TealDarkBg,          // #112A31
    onBackground = TextPrimary,
    surface = TealCardSurface,        // #16353E
    onSurface = TextPrimary,
    surfaceVariant = TealSurfaceHigher,
    onSurfaceVariant = TextSecondary,
    outline = TealBorder,
    outlineVariant = TealBorderSubtle,
    error = RoseRisk,
    onError = Color.White
)

private val LightColorScheme = darkColorScheme(
    primary = GoldWarm,
    onPrimary = TealDarkBg,
    background = TealDarkBg,
    surface = TealCardSurface,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                val insetsController = WindowCompat.getInsetsController(window, view)
                insetsController.isAppearanceLightStatusBars = false
                insetsController.isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
