package solutions.sgbrightkit.cinemaflow.data.model

import solutions.sgbrightkit.cinemaflow.Movie

fun TmdbMovie.toMovie(): Movie {
    val imageBaseUrl = "https://image.tmdb.org/t/p/"

    return Movie(
        id = this.id,
        title = this.title,
        description = this.overview,
        studio = "TMDB",  // TMDB doesn't provide studio info in basic call
        cardImageUrl = if (backdropPath != null) "${imageBaseUrl}w500${backdropPath}" else "",
        backdropUrl = if (backdropPath != null) "${imageBaseUrl}original${backdropPath}" else "",
        releaseYear = releaseDate?.take(4) ?: "Unknown",
        rating = voteAverage,
        duration = "120 min",  // TMDB needs separate API call for runtime
        genres = listOf()  // We'll map genre IDs later
    )
}