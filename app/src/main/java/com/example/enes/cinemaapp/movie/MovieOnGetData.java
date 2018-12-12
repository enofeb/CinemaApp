package com.example.enes.cinemaapp.movie;

import com.example.enes.cinemaapp.data.model.Movie;

import java.util.List;

public interface MovieOnGetData {
    void onGetData(List<Movie> movieList);
    void getMovieList(MovieOnGetData onGetData);
    void requestDataFromServer();
}
