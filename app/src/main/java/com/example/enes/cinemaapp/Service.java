package com.example.enes.cinemaapp;

import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;

import com.example.enes.cinemaapp.Model.MovieGetting;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Service {


    //Type of MovieGetting object
    @GET("movie/popular")
    Call<MovieGetting> getPopularMovies(@Query("api_key") String apiKey);


}
