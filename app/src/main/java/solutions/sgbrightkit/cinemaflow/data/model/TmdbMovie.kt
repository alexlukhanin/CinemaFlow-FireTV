package solutions.sgbrightkit.cinemaflow.data.model

import com.google.gson.annotations.SerializedName

data class TmdbMovieResponse(
    val page: Int,
    val results: List<TmdbMovie>
)

data class TmdbMovie(
    val id: Long,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("genre_ids") val genreIds: List<Int>
)