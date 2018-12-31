package com.example.enes.cinemaapp.service;

import com.example.enes.cinemaapp.data.model.Cast;
import com.example.enes.cinemaapp.data.model.CastGetting;
import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.data.model.MovieGetting;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Service {

    @GET("movie/popular")
    Observable<MovieGetting<Movie>> getPopularMovies(@Query("page") Integer pageNo);

    @GET("movie/{movie_id}")
    Observable<MovieGetting<Movie>> getMovieCredits(@Path("movie_id") long movieId, @Query("append_to_response") String credits);
}
