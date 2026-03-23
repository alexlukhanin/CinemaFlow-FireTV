package solutions.sgbrightkit.cinemaflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavHostController
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import solutions.sgbrightkit.cinemaflow.BuildConfig
import solutions.sgbrightkit.cinemaflow.Movie
import solutions.sgbrightkit.cinemaflow.Screen
import solutions.sgbrightkit.cinemaflow.data.RetrofitInstance
import solutions.sgbrightkit.cinemaflow.data.model.toMovie

private const val SEARCH_DEBOUNCE_MS = 600L

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var popularMovies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var isSearching by remember { mutableStateOf(false) }
    var keyboardVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var debounceJob by remember { mutableStateOf<Job?>(null) }

    // Load popular movies shown when search bar is empty
    LaunchedEffect(Unit) {
        try {
            val response = RetrofitInstance.api.getPopularMovies(BuildConfig.TMDB_API_KEY)
            popularMovies = response.results.map { it.toMovie() }
        } catch (e: Exception) {
            println("Popular movies error: ${e.message}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Text(
            text = "Search",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // ── Search bar (focusable, press OK to open keyboard) ─────────────
        Button(
            onClick = { keyboardVisible = !keyboardVisible },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(bottom = 4.dp),
            shape = ButtonDefaults.shape(shape = RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.primary
            ),
            contentPadding = PaddingValues(horizontal = 16.dp),
            scale = ButtonDefaults.scale(focusedScale = 1.02f)
        ) {
            androidx.compose.foundation.layout.Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = when {
                        searchQuery.isNotEmpty() -> "🔍  $searchQuery"
                        keyboardVisible -> "🔍  Start typing…"
                        else -> "🔍  Press OK to search"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        // ── Keyboard (shown only when search bar was activated) ───────────
        if (keyboardVisible) {
            TvKeyboard(
                query = searchQuery,
                onKeyPress = { key ->
                    searchQuery = when {
                        key.isEmpty() -> searchQuery.dropLast(1)   // ⌫ backspace
                        else -> searchQuery + key
                    }

                    // Debounced search trigger
                    debounceJob?.cancel()
                    if (searchQuery.isNotEmpty()) {
                        debounceJob = scope.launch {
                            delay(SEARCH_DEBOUNCE_MS)
                            isSearching = true
                            try {
                                val response = RetrofitInstance.api.searchMovies(
                                    apiKey = BuildConfig.TMDB_API_KEY,
                                    query = searchQuery
                                )
                                searchResults = response.results.map { it.toMovie() }
                            } catch (e: Exception) {
                                println("Search error: ${e.message}")
                            }
                            isSearching = false
                        }
                    } else {
                        searchResults = emptyList()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 16.dp)
            )
        }

        // ── Results / popular ─────────────────────────────────────────────
        when {
            isSearching -> Text(
                text = "Searching…",
                color = MaterialTheme.colorScheme.onBackground
            )

            searchQuery.isNotEmpty() -> {
                Text(
                    text = "Results for \"$searchQuery\" (${searchResults.size})",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 16.dp)
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

            !keyboardVisible -> {
                Text(
                    text = "Popular Movies",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 16.dp)
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