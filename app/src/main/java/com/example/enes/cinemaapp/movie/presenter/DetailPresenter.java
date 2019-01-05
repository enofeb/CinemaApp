package com.example.enes.cinemaapp.movie.presenter;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.enes.cinemaapp.data.DataManager;
import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.movie.contract.DetailContract;
import io.reactivex.Scheduler;

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
    public void getDetailList(final DetailContract.CastPresenter castPresenter, Integer movieId) {

     mCompositeDisposable.add(mDataManager.getCastFromLocal(movieId).subscribeOn(mioScheduler)
                        .observeOn(mMainScheduler)
                        .subscribe(movie -> castPresenter.onGetCastData(movie)
                        ,error-> Log.e("DP","getDetailList"+error.getMessage())));

    }
}
