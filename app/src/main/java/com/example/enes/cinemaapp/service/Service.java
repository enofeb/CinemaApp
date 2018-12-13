package com.example.enes.cinemaapp.service;

import com.example.enes.cinemaapp.data.model.MovieGetting;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Service {

    //Type of MovieGetting object
    @GET("movie/popular")
    Call<MovieGetting> getPopularMovies();

}
