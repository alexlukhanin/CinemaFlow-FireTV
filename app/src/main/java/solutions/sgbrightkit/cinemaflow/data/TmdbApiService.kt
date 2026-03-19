package solutions.sgbrightkit.cinemaflow.data

import retrofit2.http.GET
import retrofit2.http.Query
import solutions.sgbrightkit.cinemaflow.data.model.TmdbMovieResponse
import retrofit2.http.Path
import solutions.sgbrightkit.cinemaflow.data.model.TmdbMovieDetails

interface TmdbApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): TmdbMovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): TmdbMovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): TmdbMovieResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): TmdbMovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String,
        @Query("append_to_response") appendToResponse: String = "videos"
    ): TmdbMovieDetails
}