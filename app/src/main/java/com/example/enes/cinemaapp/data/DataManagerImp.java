package com.example.enes.cinemaapp.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.enes.cinemaapp.data.database.IDatabase;
import com.example.enes.cinemaapp.data.model.Cast;
import com.example.enes.cinemaapp.data.model.CastGetting;
import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.data.model.MovieGetting;
import com.example.enes.cinemaapp.service.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


@Singleton
public class DataManagerImp implements DataManager {

    private static final String TAG=DataManagerImp.class.getName();

    @NonNull
    private final   Service mService;

    @NonNull
    private final IDatabase mIDatabase;

    private final HashMap<Long,List<Movie>> castCache;

    public DataManagerImp(@NonNull Service mService, @NonNull IDatabase mIDatabase) {
        this.mService = mService;
        this.mIDatabase = mIDatabase;
        this.castCache=new HashMap<>();
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


    @Override
    public Observable<List<Movie>> getDatasFromLocal() {
        //Kaydolan veriler ve serviceden gelen veriler kaydedlip birleştirilyr
        return Observable.concat(mIDatabase.fetchMoviesObservable().toList(),getMovies(null))
                .filter(movieList -> movieList!=null&&movieList.size()>0).first();
    }

    @Override
    public Observable<Movie> getCast(long movieId, String credits) {

        Observable<Movie> castObservable= mService.getMovieCredits(movieId,credits)
                .subscribeOn(Schedulers.io())
                .map(movieMovieGetting -> movieMovieGetting.getResults())
                .flatMap(movieList -> Observable.from(movieList));
        return castObservable;
    }


    public HashMap<Long,List<Movie>> getCastCache(){return castCache;}

    private void clearMovies(MovieGetting<Movie> movieDiscoverResponse) {
        if (movieDiscoverResponse.getPage() == 1) {
            mIDatabase.clearMovies();
        }
    }
}
