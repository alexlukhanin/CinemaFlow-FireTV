package solutions.sgbrightkit.cinemaflow

class Movie(
    val id: Long,
    val title: String,
    val description: String,
    val studio: String,
    val cardImageUrl: String = "https://picsum.photos/seed/$id/313/176"
) {
}