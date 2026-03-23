package solutions.sgbrightkit.cinemaflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import kotlinx.coroutines.launch
import solutions.sgbrightkit.cinemaflow.BuildConfig
import solutions.sgbrightkit.cinemaflow.Movie
import solutions.sgbrightkit.cinemaflow.Screen
import solutions.sgbrightkit.cinemaflow.data.RetrofitInstance
import solutions.sgbrightkit.cinemaflow.data.model.toMovie

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    restoredQuery: String = "",
    onQueryCommitted: (String) -> Unit = {}
) {
    var pendingQuery by remember { mutableStateOf("") }
    // Init searchQuery from the caller so returning from Details restores the last search
    var searchQuery by remember { mutableStateOf(restoredQuery) }
    var searchResults by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var popularMovies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var isSearching by remember { mutableStateOf(false) }
    var keyboardVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Load popular movies on first open
    LaunchedEffect(Unit) {
        try {
            val response = RetrofitInstance.api.getPopularMovies(BuildConfig.TMDB_API_KEY)
            popularMovies = response.results.map { it.toMovie() }
        } catch (e: Exception) {
            println("Popular movies error: ${e.message}")
        }
    }

    // Re-fetch results when returning from Details with an existing query
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotEmpty() && searchResults.isEmpty()) {
            isSearching = true
            try {
                val response = RetrofitInstance.api.searchMovies(
                    apiKey = BuildConfig.TMDB_API_KEY,
                    query = searchQuery
                )
                searchResults = response.results.map { it.toMovie() }
            } catch (e: Exception) {
                println("Search restore error: ${e.message}")
            }
            isSearching = false
        }
    }

    // ── Keyboard popup ────────────────────────────────────────────────────
    if (keyboardVisible) {
        Dialog(
            onDismissRequest = { keyboardVisible = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(0.90f),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Query preview inside the popup
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = if (pendingQuery.isEmpty()) "🔍  Start typing…" else "🔍  $pendingQuery",
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (pendingQuery.isEmpty())
                                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                            else
                                MaterialTheme.colorScheme.onBackground
                        )
                    }

                    TvKeyboard(
                        onKeyPress = { key ->
                            pendingQuery = when {
                                key.isEmpty() -> pendingQuery.dropLast(1)
                                else -> pendingQuery + key
                            }
                        },
                        onSearch = {
                            val query = pendingQuery.trim()
                            if (query.isNotEmpty()) {
                                keyboardVisible = false
                                searchQuery = query
                                onQueryCommitted(query)   // persist in parent
                                scope.launch {
                                    isSearching = true
                                    try {
                                        val response = RetrofitInstance.api.searchMovies(
                                            apiKey = BuildConfig.TMDB_API_KEY,
                                            query = query
                                        )
                                        searchResults = response.results.map { it.toMovie() }
                                    } catch (e: Exception) {
                                        println("Search error: ${e.message}")
                                    }
                                    isSearching = false
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    // ── Main layout (never shifts) ─────────────────────────────────────────
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Search",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )

        // Search bar – styled exactly like SettingItem / other buttons in the app
        Button(
            onClick = {
                pendingQuery = searchQuery  // pre-fill with last query so user can refine
                keyboardVisible = true
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
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
                        text = if (searchQuery.isEmpty()) "🔍  Press OK to search…" else "🔍  $searchQuery",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = "⌨", style = MaterialTheme.typography.headlineMedium)
                }
            }
        }

        // ── Results / popular ───────────────────────────────────────────────
        when {
            isSearching -> Text(
                text = "Searching…",
                color = MaterialTheme.colorScheme.onBackground
            )

            searchQuery.isNotEmpty() -> {
                Text(
                    text = "Results for \"$searchQuery\" (${searchResults.size})",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(searchResults.size) { index ->
                        MovieCard(
                            movie = searchResults[index],
                            onClick = {
                                navController.navigate(
                                    Screen.Details.createRoute(searchResults[index].id)
                                )
                            }
                        )
                    }
                }
            }

            else -> {
                Text(
                    text = "Popular Movies",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(popularMovies.size) { index ->
                        MovieCard(
                            movie = popularMovies[index],
                            onClick = {
                                navController.navigate(
                                    Screen.Details.createRoute(popularMovies[index].id)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}