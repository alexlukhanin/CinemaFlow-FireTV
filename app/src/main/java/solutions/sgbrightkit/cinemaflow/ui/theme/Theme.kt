package solutions.sgbrightkit.cinemaflow.ui.theme

import androidx.compose.runtime.Composable
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.darkColorScheme

@OptIn(ExperimentalTvMaterial3Api::class)
private val CinemaFlowColorScheme = darkColorScheme(
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
@Composable
fun CinemaFlowTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CinemaFlowColorScheme,
        typography = Typography,
        content = content
    )
}