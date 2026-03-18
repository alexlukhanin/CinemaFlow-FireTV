package solutions.sgbrightkit.cinemaflow

object MovieList {

    val categories = listOf(
        "Action" to listOf(
            Movie(
                id = 1,
                title = "Action Hero",
                description = "An explosive adventure awaits in this thrilling action-packed journey. Follow our hero as they battle against impossible odds to save the world from impending doom.",
                studio = "Action Studios",
                releaseYear = "2023",
                rating = 8.5,
                duration = "145 min",
                genres = listOf("Action", "Adventure", "Thriller")
            ),
            Movie(2, "Fast Chase", "High-speed pursuit thriller", "Action Studios"),
            Movie(3, "Battle Zone", "Epic battles ahead", "Action Studios"),
            Movie(4, "Night Raid", "Mission impossible begins", "Action Studios")
        ),
        "Drama" to listOf(
            Movie(11, "Emotional Journey", "A touching story", "Drama Productions"),
            Movie(12, "Life Stories", "Real people, real emotions", "Drama Productions"),
            Movie(13, "The Last Letter", "A message from the past", "Drama Productions")
        ),
        "Comedy" to listOf(
            Movie(21, "Laugh Out Loud", "Non-stop comedy", "Comedy Central"),
            Movie(22, "Funny Business", "Office humor at its best", "Comedy Central")
        )
    )
}