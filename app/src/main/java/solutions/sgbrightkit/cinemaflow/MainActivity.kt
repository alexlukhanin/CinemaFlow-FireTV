package solutions.sgbrightkit.cinemaflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import solutions.sgbrightkit.cinemaflow.ui.theme.CinemaFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            CinemaFlowTheme(useDarkTheme = isDarkTheme) {
                CinemaFlowNavGraph(
                    onThemeToggle = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }
}