package solutions.sgbrightkit.cinemaflow.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.tv.material3.Card
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import solutions.sgbrightkit.cinemaflow.BuildConfig
import solutions.sgbrightkit.cinemaflow.Movie
import solutions.sgbrightkit.cinemaflow.Screen
import solutions.sgbrightkit.cinemaflow.data.RetrofitInstance
import solutions.sgbrightkit.cinemaflow.data.model.toMovie

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    var popularMovies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var topRatedMovies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var upcomingMovies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    // Fetch all categories
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val popular = RetrofitInstance.api.getPopularMovies(BuildConfig.TMDB_API_KEY)
                val topRated = RetrofitInstance.api.getTopRatedMovies(BuildConfig.TMDB_API_KEY)
                val upcoming = RetrofitInstance.api.getUpcomingMovies(BuildConfig.TMDB_API_KEY)

                popularMovies = popular.results.map { it.toMovie() }
                topRatedMovies = topRated.results.map { it.toMovie() }
                upcomingMovies = upcoming.results.map { it.toMovie() }

                isLoading = false
            } catch (e: Exception) {
                println("Error: ${e.message}")
                isLoading = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Loading movies...")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp, 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(bottom = 40.dp)
            ) {
                // Popular Movies
                item {
                    MovieCategory(
                        categoryName = "Popular Movies",
                        movies = popularMovies,
                        navController = navController
                    )
                }

                // Top Rated
                item {
                    MovieCategory(
                        categoryName = "Top Rated",
                        movies = topRatedMovies,
                        navController = navController
                    )
                }

                // Upcoming
                item {
                    MovieCategory(
                        categoryName = "Coming Soon",
                        movies = upcomingMovies,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun MovieCategory(
    categoryName: String,
    movies: List<Movie>,
    navController: NavHostController
) {
    Column {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(movies.size) { index ->
                MovieCard(
                    movie = movies[index],
                    onClick = {
                        navController.navigate(Screen.Details.createRoute(movies[index].id))
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(240.dp)
            .height(135.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = movie.cardImageUrl,
                contentDescription = movie.title,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}