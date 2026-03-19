package solutions.sgbrightkit.cinemaflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.tv.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    onThemeToggle: () -> Unit = {}
    ) {
    var isDarkTheme by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(48.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Theme toggle
        SettingItem(
            title = "Theme",
            description = if (isDarkTheme) "Dark Mode" else "Light Mode",
            onClick = {
                isDarkTheme = !isDarkTheme
                onThemeToggle()
            }
        )

        // Version info
        val context = androidx.compose.ui.platform.LocalContext.current
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val versionName = packageInfo.versionName

        // About
        SettingItem(
            title = "About",
            description = "CinemaFlow v$versionName",
            onClick = { /* TODO */ }
        )

        Text(
            text = "Version $versionName",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SettingItem(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$title: $description",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = "›", style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}