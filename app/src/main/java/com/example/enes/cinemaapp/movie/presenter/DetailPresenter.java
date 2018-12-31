package com.example.enes.cinemaapp.movie.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.enes.cinemaapp.data.DataManager;
import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.movie.contract.DetailContract;
import com.example.enes.cinemaapp.service.Service;

import org.reactivestreams.Subscriber;

import javax.inject.Inject;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


import static com.example.enes.cinemaapp.utils.Constants.CREDITS;

public class DetailPresenter extends BasePresenter<DetailContract.CastView> implements DetailContract.CastPresenter {
    public static final String TAG="Detail Activity";
    @NonNull
    private final DataManager mDataManager;

    @NonNull
    private  final Scheduler mMainScheduler,mioScheduler;

    public DetailPresenter(@NonNull DataManager dataManager, @NonNull Scheduler mainScheduler, @NonNull Scheduler ioScheduler) {
        this.mDataManager = dataManager;
        this.mMainScheduler = mainScheduler;
        this.mioScheduler = ioScheduler;
    }


    @Override
    public void requestMovieData(int movieId) {
        getDetailList(this,movieId);
    }

    @Override
    public void onGetCastData(Movie movie) {
        mView.setToView(movie);
    }

    @Override
    public void getDetailList(final DetailContract.CastPresenter castPresenter, int movieId) {

        mCompositeDisposable.add(mDataManager.getCast(movieId,CREDITS).subscribeOn(mioScheduler)
                        .observeOn(mMainScheduler)
                        .subscribe(movie -> castPresenter.onGetCastData(movie)
                        ,error-> Log.e(TAG,"getDetailList"+error.getMessage())));


      /*  mCompositeDisposable.add( mService.getMovieCredits(movieId,CREDITS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie -> castPresenter.onGetCastData(movie)));*/
    }
}
