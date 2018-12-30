package com.example.enes.cinemaapp.movie.presenter;

import android.support.annotation.NonNull;

import com.example.enes.cinemaapp.data.DataManager;
import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.movie.contract.MovieListContract;
import com.example.enes.cinemaapp.service.Service;
import java.util.List;

import javax.inject.Inject;

import rx.Scheduler;
import rx.Subscriber;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MoviePresenter extends BasePresenter<MovieListContract.MovieView> implements MovieListContract.MoviePresenter {


    @NonNull
    private final DataManager dataManager;
    @NonNull
    private  final Scheduler mainScheduler,ioScheduler;

   // public Service mService;
    //@Inject
    //public MoviePresenter(Service service) {
      //  this.mService=service;
    //}

    public MoviePresenter(@NonNull DataManager dataManager, Scheduler mainScheduler, Scheduler ioScheduler) {
        this.dataManager = dataManager;
        this.mainScheduler = mainScheduler;
        this.ioScheduler = ioScheduler;
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

        addSubscription(dataManager.getDatasFromLocal().subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(new Subscriber<List<Movie>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Movie> movieList) {
                      movieListContract.onGetData(movieList);
                    }
                })
        );

      /*  mCompositeDisposable.add( mService.getPopularMovies(pageNo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie->movieListContract.onGetData(movie.getResults())));*/

    }
}


