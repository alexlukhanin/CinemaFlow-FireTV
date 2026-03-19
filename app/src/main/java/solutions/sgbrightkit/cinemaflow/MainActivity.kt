package solutions.sgbrightkit.cinemaflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import solutions.sgbrightkit.cinemaflow.ui.theme.CinemaFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            var isDarkTheme by remember {
                mutableStateOf(PreferencesManager.isDarkTheme(context))
            }

            CinemaFlowTheme(useDarkTheme = isDarkTheme) {
                CinemaFlowNavGraph(
                    onThemeToggle = {
                        isDarkTheme = !isDarkTheme
                        PreferencesManager.saveDarkTheme(context, isDarkTheme)
                    }
                )
            }
        }
    }
}