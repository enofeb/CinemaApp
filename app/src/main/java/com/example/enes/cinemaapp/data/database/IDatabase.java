package com.example.enes.cinemaapp.data.database;
import com.example.enes.cinemaapp.data.model.Movie;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;

public interface IDatabase {

    void saveMovie(Movie movie);

    void updateMovie(Movie movie);

    List<Movie> fetchMovies();

    Observable<Movie> fetchMoviesObservable();

    void clearMovies();

    Movie fetchCast(Integer movieId);

    Single<Movie> fetchCastSingle(Integer movieId);
}
