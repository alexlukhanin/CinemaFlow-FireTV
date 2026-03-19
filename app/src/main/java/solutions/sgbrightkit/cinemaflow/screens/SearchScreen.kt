package solutions.sgbrightkit.cinemaflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.tv.material3.*
import solutions.sgbrightkit.cinemaflow.MovieList
import solutions.sgbrightkit.cinemaflow.Movie
import solutions.sgbrightkit.cinemaflow.Screen

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }

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

        // Search bar (placeholder - will add keyboard later)
        Button(
            onClick = { /* TODO: Open on-screen keyboard */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(bottom = 40.dp),
            scale = ButtonDefaults.scale(focusedScale = 1.05f)
        ) {
            Text(
                text = if (searchQuery.isEmpty()) "🔍 Type to search..." else searchQuery,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // Results (when we have TMDB API)
        if (searchQuery.isNotEmpty()) {
            Text(
                text = "Search results for: \"$searchQuery\"",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // TODO: Show actual search results from TMDB
        } else {
            // Suggested/Popular content
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
                items(MovieList.categories.first().second.size) { index ->
                    MovieCard(
                        movie = MovieList.categories.first().second[index],
                        onClick = {
                            navController.navigate(
                                Screen.Details.createRoute(MovieList.categories.first().second[index].id)
                            )
                        }
                    )
                }
            }
        }
    }
}