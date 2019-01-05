package com.example.enes.cinemaapp.data;
import com.example.enes.cinemaapp.data.model.Movie;
import java.util.List;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface DataManager {

    Single<List<Movie>> getMovies(Integer page);

    Maybe<List<Movie>> getMoviesFromLocal();

    Single<Movie> getCast(Integer movieId, String credits);

    Maybe<Movie> getCastFromLocal(Integer movieId);

}
