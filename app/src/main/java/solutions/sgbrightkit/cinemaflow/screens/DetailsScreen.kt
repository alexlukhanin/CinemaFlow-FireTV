package solutions.sgbrightkit.cinemaflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.tv.material3.*
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import solutions.sgbrightkit.cinemaflow.BuildConfig
import solutions.sgbrightkit.cinemaflow.Movie
import solutions.sgbrightkit.cinemaflow.data.RetrofitInstance
import solutions.sgbrightkit.cinemaflow.data.model.toMovie

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavHostController, movieId: Long?) {
    var movie by remember { mutableStateOf<Movie?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var loadError by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Fetch full movie details
    LaunchedEffect(movieId) {
        if (movieId != null) {
            scope.launch {
                try {
                    val details = RetrofitInstance.api.getMovieDetails(
                        movieId = movieId,
                        apiKey = BuildConfig.TMDB_API_KEY
                    )
                    movie = details.toMovie()
                    isLoading = false
                } catch (e: Exception) {
                    println("Error loading details: ${e.message}")
                    loadError = true
                    isLoading = false
                }
            }
        } else {
            loadError = true
            isLoading = false
        }
    }

    // Loading state
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...")
        }
        return
    }

    // Error state
    if (loadError || movie == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Movie not found")
        }
        return
    }

    // Success state - safe to use movie with let
    movie?.let { currentMovie ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Background image
            AsyncImage(
                model = currentMovie.backdropUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.3f),
                                Color.Black.copy(alpha = 0.9f)
                            )
                        )
                    )
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(48.dp)
            ) {
                Text(
                    text = currentMovie.title,
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Info row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = currentMovie.releaseYear,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Text(
                        text = "⭐ ${String.format("%.1f", currentMovie.rating)}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFFFFD700)
                    )
                    Text(
                        text = currentMovie.duration,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Genres
                Text(
                    text = currentMovie.genres.joinToString(" • "),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.6f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Play button
                Button(
                    onClick = {
                        // TODO: Launch video player
                    },
                    modifier = Modifier.width(200.dp)
                ) {
                    Text("▶ Play Movie")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = currentMovie.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }
    }
}