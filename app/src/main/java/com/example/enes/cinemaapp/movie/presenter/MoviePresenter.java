package com.example.enes.cinemaapp.movie.presenter;

import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.movie.contract.MovieListContract;
import com.example.enes.cinemaapp.service.Service;
import java.util.List;

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

        mCompositeDisposable.add( mService.getPopularMovies(pageNo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie->movieListContract.onGetData(movie.getResults())));

    }
}


