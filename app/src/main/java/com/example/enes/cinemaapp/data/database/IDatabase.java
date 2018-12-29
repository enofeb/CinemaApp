package com.example.enes.cinemaapp.data.database;

import com.example.enes.cinemaapp.data.model.Movie;
import rx.Observable;
import java.util.List;

public interface IDatabase {

    void saveMovie(Movie movie);

    List<Movie> fetchMovies();

    Observable<Movie> fetchMoviesObservable();

    void clearMovies();
}
