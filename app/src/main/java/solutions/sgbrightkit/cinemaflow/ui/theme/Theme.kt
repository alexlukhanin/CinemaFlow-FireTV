package solutions.sgbrightkit.cinemaflow.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.lightColorScheme
import androidx.tv.material3.darkColorScheme

@OptIn(ExperimentalTvMaterial3Api::class)
private val LightColorScheme = lightColorScheme(
    primary = BrandPrimary,
    secondary = BrandSecondary,
    background = BrandBackground,
    surface = BrandBackground,
    onPrimary = White,
    onSecondary = White,
    onBackground = BrandText,
    onSurface = BrandText
)

@OptIn(ExperimentalTvMaterial3Api::class)
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF5037D0),  // Original purple
    secondary = Color(0xFF0AA1B2),
    background = Color(0xFF121021),  // Original dark
    surface = Color(0xFF121021),
    onPrimary = White,
    onSecondary = White,
    onBackground = Color(0xFF95959D),
    onSurface = Color(0xFF95959D)
)

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun CinemaFlowTheme(
    useDarkTheme: Boolean = false,  // Light by default
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}