package solutions.sgbrightkit.cinemaflow.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.tv.material3.*
import solutions.sgbrightkit.cinemaflow.R
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MainMenuScreen(
    navController: NavHostController,
    onThemeToggle: () -> Unit = {},
    backStackEntry: NavBackStackEntry? = null
) {
    val savedState = backStackEntry?.savedStateHandle
    var selectedMenuItem by remember {
        mutableStateOf(savedState?.get<Int>("selectedMenuItem") ?: 0)
    }
    var isMenuFocused by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }
    val activity = LocalContext.current as? ComponentActivity

    // Handle back press
    BackHandler {
        showExitDialog = true
    }

    Box(
        modifier = Modifier.fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Menu sidebar
            Column(
                modifier = Modifier
                    .width(if (isMenuFocused) 160.dp else 80.dp)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
                    .padding(16.dp)
                    .padding(top = 100.dp), // Push down for logo space
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MenuItem(
                    icon = "🏠",
                    text = "Home",
                    showText = isMenuFocused,
                    onFocusChange = { isMenuFocused = it },
                    onClick = {
                        selectedMenuItem = 0
                        savedState?.set("selectedMenuItem", 0)
                    }
                )
                MenuItem(
                    icon = "🔍",
                    text = "Search",
                    showText = isMenuFocused,
                    onFocusChange = { isMenuFocused = it },
                    onClick = {
                        selectedMenuItem = 1
                        savedState?.set("selectedMenuItem", 1)
                    }
                )
                MenuItem(
                    icon = "⚙️",
                    text = "Settings",
                    showText = isMenuFocused,
                    onFocusChange = { isMenuFocused = it },
                    onClick = {
                        selectedMenuItem = 2
                        savedState?.set("selectedMenuItem", 2)
                    }
                )
            }

            // Content area
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp)
            ) {
                when (selectedMenuItem) {
                    0 -> MainScreen(navController)
                    1 -> SearchScreen(navController)
                    2 -> SettingsScreen(navController, onThemeToggle)
                }
            }
        }

        // Logo overlay - fixed position, top-left
        Image(
            painter = painterResource(id = R.drawable.main_logo),
            contentDescription = "CinemaFlow Logo",
            modifier = Modifier
                .height(64.dp)
                .padding(16.dp)
                .align(Alignment.TopStart)
        )
    }
    // Exit Dialog
    if (showExitDialog) {
        ExitDialog(
            onStay = { showExitDialog = false },
            onExit = { activity?.finish() }
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ExitDialog(onStay: () -> Unit, onExit: () -> Unit) {
    androidx.compose.ui.window.Dialog(onDismissRequest = onStay) {
        Surface(
            modifier = Modifier
                .width(400.dp)
                .padding(24.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Exit CinemaFlow?",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Are you sure you want to exit?",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = onStay,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Stay")
                    }

                    Button(
                        onClick = onExit,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Exit")
                    }
                }
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
    onFocusChange: (Boolean) -> Unit,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(if (showText) 120.dp else 50.dp)
            .onFocusChanged { onFocusChange(it.hasFocus) }
    ) {
        Text(
            text = if (showText) "$icon $text" else icon,
            maxLines = 1
        )
    }
}