package com.example.enes.cinemaapp.movie.presenter;

import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.data.model.MovieGetting;
import com.example.enes.cinemaapp.movie.MovieListContract;
import com.example.enes.cinemaapp.movie.presenter.BasePresenter;
import com.example.enes.cinemaapp.service.Service;
import java.util.List;


import io.reactivex.functions.Consumer;


import javax.inject.Inject;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MoviePresenter extends BasePresenter<MovieListContract.MovieView> implements MovieListContract.MoviePresenter {

    public Service service;

    @Inject
    public MoviePresenter(Service service) {
        this.service=service;
    }

    @Override
    public void getMoreData(int pageNo) {
        getMovieList(this, pageNo);
    }


    @Override
    public void requestDataFromServer() {
        getMovieList(this,1);
    }

    @Override
    public void onGetData(List<Movie> movieList) {
        view.setDataToRecyclerView(movieList);
    }


    @Override
    public void getMovieList(final MovieListContract.MoviePresenter movieListContract,int pageNo) {

        service.getPopularMovies(pageNo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieGetting<Movie>>() {
                    @Override
                    public void accept(MovieGetting<Movie> movieMovieGetting) throws Exception {
                        movieListContract.onGetData(movieMovieGetting.getResults());
                    }
                });

                    //.subscribe(response->{movieListContract.onGetData(response.getResults());});

    }


}


