package com.example.enes.cinemaapp.data;

import com.example.enes.cinemaapp.data.model.Cast;
import com.example.enes.cinemaapp.data.model.Movie;

import java.util.List;
import rx.Observable;

public interface DataManager {

    Observable<List<Movie>> getMovies(Integer page);

    Observable<List<Movie>> getDatasFromLocal();

    Observable<List<Movie>> getCast(long movieId,String credits);
    //Observable<List<Cast>> getMovieCast(long movieId);
}
