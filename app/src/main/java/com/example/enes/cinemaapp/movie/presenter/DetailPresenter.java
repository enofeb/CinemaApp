package com.example.enes.cinemaapp.movie.presenter;

import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.movie.MovieListContract;
import com.example.enes.cinemaapp.service.Service;

import java.util.List;

import javax.inject.Inject;
import io.reactivex.functions.Consumer;


import javax.inject.Inject;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;


import static com.example.enes.cinemaapp.utils.Constants.CREDITS;

public class DetailPresenter extends BasePresenter<MovieListContract.CastView> implements MovieListContract.CastPresenter {

    Service service;

    @Inject
    public DetailPresenter(Service service) {
        this.service=service;
    }

    @Override
    public void requestMovieData(int movieId) {
        getDetailList(this,movieId);
    }

    @Override
    public void onGetCastData(Movie movie) {
        view.setToView(movie);
    }

    @Override
    public void getDetailList(final MovieListContract.CastPresenter castPresenter, int movieId) {
        service.getMovieCredits(movieId,CREDITS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Exception {
                        castPresenter.onGetCastData(movie);
                    }
                });

    }
}
