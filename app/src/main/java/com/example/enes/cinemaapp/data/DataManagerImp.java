package com.example.enes.cinemaapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.enes.cinemaapp.data.database.IDatabase;
import com.example.enes.cinemaapp.data.model.Cast;
import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.data.model.MovieGetting;
import com.example.enes.cinemaapp.service.Service;

import java.util.List;
import java.util.UUID;

import javax.inject.Singleton;


import rx.Observable;
//import io.reactivex.schedulers.Schedulers;
import rx.schedulers.Schedulers;


@Singleton
public class DataManagerImp implements DataManager {

    private static final String TAG=DataManagerImp.class.getName();

    @NonNull
    private  Service mService;

    @NonNull
    private IDatabase mIDatabase;

    public DataManagerImp(@NonNull Service mService, @NonNull IDatabase mIDatabase) {
        this.mService = mService;
        this.mIDatabase = mIDatabase;
    }


    @Override
    public Observable<List<Movie>> getMovies(@Nullable Integer page) {

        Observable<Movie> movieObservable=mService.getPopularMovies(page)
                .subscribeOn(Schedulers.io())
                .doOnNext(movieMovieGetting -> clearMovies(movieMovieGetting))
                .map(movieMovieGetting -> movieMovieGetting.getResults())
                .flatMap(movieList -> Observable.from(movieList))
                .doOnNext(movie -> {
                    String realId=UUID.randomUUID().toString();
                    movie.setRealId(realId);
                    mIDatabase.saveMovie(movie);
                });

        return movieObservable.toList();
    }

    private void clearMovies(MovieGetting<Movie> movieDiscoverResponse) {
        if (movieDiscoverResponse.getPage() == 1) {
            mIDatabase.clearMovies();
        }
    }
}
