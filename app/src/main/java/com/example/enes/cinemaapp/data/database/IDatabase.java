package com.example.enes.cinemaapp.data.database;

import com.example.enes.cinemaapp.data.model.CastGetting;
import com.example.enes.cinemaapp.data.model.Movie;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;

public interface IDatabase {

    void saveMovie(Movie movie);

    void updateMovie(Movie movie, CastGetting castGetting);

    List<Movie> fetchMovies();

    Observable<Movie> fetchMoviesObservable();

    void clearMovies();

    Single<Movie> getMovie(long movieId);
}
