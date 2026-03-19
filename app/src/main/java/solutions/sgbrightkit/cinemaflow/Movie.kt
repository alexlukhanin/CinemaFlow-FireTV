package solutions.sgbrightkit.cinemaflow

data class Movie(
    val id: Long,
    val title: String,
    val description: String,
    val studio: String,
    val cardImageUrl: String = "https://picsum.photos/seed/$id/313/176",
    val backdropUrl: String = "https://picsum.photos/seed/${id}bg/1280/720",
    val releaseYear: String = "2024",
    val rating: Double = 7.5,
    val duration: String = "120 min",
    val genres: List<String> = listOf("Action", "Drama"),
    val trailerKey: String? = null  // ADD THIS LINE
)