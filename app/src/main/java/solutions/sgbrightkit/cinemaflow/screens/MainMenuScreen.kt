package solutions.sgbrightkit.cinemaflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.tv.material3.*

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MainMenuScreen(navController: NavHostController) {
    var selectedMenuItem by remember { mutableStateOf(0) }
    var isMenuFocused by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxSize()) {
        // Left sidebar menu
        Column(
            modifier = Modifier
                .width(if (isMenuFocused) 180.dp else 80.dp)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
                .padding(16.dp)
                .onFocusChanged { isMenuFocused = it.hasFocus },
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            MenuItem(
                icon = "🏠",
                text = "Home",
                showText = isMenuFocused,
                onClick = { selectedMenuItem = 0 }
            )
            MenuItem(
                icon = "🔍",
                text = "Search",
                showText = isMenuFocused,
                onClick = { selectedMenuItem = 1 }
            )
            MenuItem(
                icon = "⚙️",
                text = "Settings",
                showText = isMenuFocused,
                onClick = { selectedMenuItem = 2 }
            )
        }

        // Right content area
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp)
        ) {
            when (selectedMenuItem) {
                0 -> MainScreen(navController)
                1 -> SearchScreen(navController)
                2 -> SettingsScreen(navController)
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MenuItem(
    icon: String,
    text: String,
    showText: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.width(if (showText) 120.dp else 50.dp)
    ) {
        Text(
            text = if (showText) "$icon $text" else icon,
            maxLines = 1
        )
    }
}