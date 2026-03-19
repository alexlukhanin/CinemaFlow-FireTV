package solutions.sgbrightkit.cinemaflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.tv.material3.*
import kotlinx.coroutines.launch
import solutions.sgbrightkit.cinemaflow.BuildConfig
import solutions.sgbrightkit.cinemaflow.Movie
import solutions.sgbrightkit.cinemaflow.Screen
import solutions.sgbrightkit.cinemaflow.data.RetrofitInstance
import solutions.sgbrightkit.cinemaflow.data.model.toMovie


@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var popularMovies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var isSearching by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Load popular movies initially
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = RetrofitInstance.api.getPopularMovies(BuildConfig.TMDB_API_KEY)
                popularMovies = response.results.map { it.toMovie() }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
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

        // Search bar (placeholder for now)
        Button(
            onClick = {
                // TODO: Open on-screen keyboard
                // For testing, search for "action"
                searchQuery = "action"
                scope.launch {
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
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(bottom = 40.dp),
            scale = ButtonDefaults.scale(focusedScale = 1.05f)
        ) {
            Text(
                text = if (searchQuery.isEmpty()) "🔍 Type to search..." else "Search: $searchQuery",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // Show results or popular
        if (isSearching) {
            Text(
                text = "Searching...",
                color = MaterialTheme.colorScheme.onBackground
                )
        } else if (searchQuery.isNotEmpty()) {
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
                            navController.navigate(Screen.Details.createRoute(searchResults[index].id))
                        }
                    )
                }
            }
        } else {
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
                            navController.navigate(Screen.Details.createRoute(popularMovies[index].id))
                        }
                    )
                }
            }
        }
    }
}