package com.example.enes.cinemaapp.service;

import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.data.model.MovieGetting;

import retrofit2.Call;
import retrofit2.http.GET;
import io.reactivex.Observable;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Service {

    @GET("movie/popular")
    Observable<MovieGetting<Movie>> getPopularMovies(@Query("page") int pageNo);

    @GET("movie/{movie_id}")
    Observable<Movie> getMovieCredits(@Path("movie_id") long movieId,@Query("append_to_response") String credits);
}
