package com.example.enes.cinemaapp.movie.presenter;

import android.util.Log;

import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.data.model.MovieGetting;
import com.example.enes.cinemaapp.movie.MovieListContract;
import com.example.enes.cinemaapp.service.Service;
import java.util.List;


import io.reactivex.observers.DisposableObserver;


import javax.inject.Inject;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.Observable;

public class MoviePresenter extends BasePresenter<MovieListContract.MovieView> implements MovieListContract.MoviePresenter {

    public Service service;

    @Inject
    public MoviePresenter(Service service) {
        this.service=service;
    }

    @Override
    public void requestDataFromServer() {
      getMovieList(this);
    }

    @Override
    public void onGetData(List<Movie> movieList) {
        view.setDataToRecyclerView(movieList);
    }

    @Override
    public void getMovieList(final MovieListContract.MoviePresenter movieListContract) {

            service.getPopularMovies().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response->{movieListContract.onGetData(response.getResults());});

    }

}


