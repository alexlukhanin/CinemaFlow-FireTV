package solutions.sgbrightkit.cinemaflow
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.ExperimentalTvMaterial3Api
import solutions.sgbrightkit.cinemaflow.screens.DetailsScreen
import solutions.sgbrightkit.cinemaflow.screens.MainScreen
import solutions.sgbrightkit.cinemaflow.screens.SearchScreen
import solutions.sgbrightkit.cinemaflow.screens.SettingsScreen

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Search : Screen("search")
    object Details : Screen("details/{movieId}") {
        fun createRoute(movieId: Long) = "details/$movieId"
    }
    object Settings : Screen("settings")
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun CinemaFlowNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(navController = navController)
        }

        composable(Screen.Search.route) {
            SearchScreen(navController = navController)
        }

        composable(Screen.Details.route) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toLongOrNull()
            DetailsScreen(navController = navController, movieId = movieId)
        }

        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
    }
}