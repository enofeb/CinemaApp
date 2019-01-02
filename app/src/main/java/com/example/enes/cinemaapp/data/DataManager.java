package com.example.enes.cinemaapp.data;

import com.example.enes.cinemaapp.data.model.Cast;
import com.example.enes.cinemaapp.data.model.CastGetting;
import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.data.model.MovieGetting;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;


public interface DataManager {

    Single<List<Movie>> getMovies(Integer page);

    Maybe<List<Movie>> getDatasFromLocal();

    Single<Movie> getCast(long movieId, String credits);


    //Observable<List<Cast>> getMovieCast(long movieId);
}
