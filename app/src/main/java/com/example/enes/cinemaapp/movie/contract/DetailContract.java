package com.example.enes.cinemaapp.movie.contract;

import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.movie.MvpPresenter;
import com.example.enes.cinemaapp.movie.MvpView;

public interface DetailContract {

    interface CastPresenter extends MvpPresenter<CastView> {
        void getDetailList(DetailContract.CastPresenter castPresenter, Integer movieId);
        void requestMovieData(int movieId);
        void onGetCastData(Movie movie);
    }
    interface CastView extends MvpView {
        void setToView(Movie movie);
    }
}
