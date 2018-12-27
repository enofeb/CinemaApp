package com.example.enes.cinemaapp.movie.presenter;

import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.data.model.MovieGetting;
import com.example.enes.cinemaapp.movie.MovieListContract;
import com.example.enes.cinemaapp.service.Service;
import java.util.List;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MoviePresenter extends BasePresenter<MovieListContract.MovieView> implements MovieListContract.MoviePresenter {

    public Service mService;

    @Inject
    public MoviePresenter(Service service) {
        this.mService=service;
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
        mView.setDataToRecyclerView(movieList);
    }

    @Override
    public void getMovieList(final MovieListContract.MoviePresenter movieListContract,int pageNo) {

        mService.getPopularMovies(pageNo).subscribeOn(Schedulers.io())
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


