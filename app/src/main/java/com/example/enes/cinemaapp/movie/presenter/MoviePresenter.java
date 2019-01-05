package com.example.enes.cinemaapp.movie.presenter;

import android.support.annotation.NonNull;

import com.example.enes.cinemaapp.data.DataManager;
import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.movie.contract.MovieListContract;
import com.example.enes.cinemaapp.service.Service;

import org.reactivestreams.Subscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MoviePresenter extends BasePresenter<MovieListContract.MovieView> implements MovieListContract.MoviePresenter {


    @NonNull
    private final DataManager mDataManager;
    @NonNull
    private  final Scheduler mMainScheduler,ioScheduler;

   // public Service mService;
    //@Inject
    //public MoviePresenter(Service service) {
      //  this.mService=service;
    //}

    public MoviePresenter(@NonNull DataManager dataManager, Scheduler mainScheduler, Scheduler ioScheduler) {
        this.mDataManager = dataManager;
        this.mMainScheduler = mainScheduler;
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

        mCompositeDisposable.add(mDataManager.getMoviesFromLocal().subscribeOn(ioScheduler)
                .observeOn(mMainScheduler).subscribe(movieList -> movieListContract.onGetData(movieList)));

      /*  mCompositeDisposable.add( mService.getPopularMovies(pageNo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie->movieListContract.onGetData(movie.getResults())));*/

    }
}


