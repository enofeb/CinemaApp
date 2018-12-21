package com.example.enes.cinemaapp.movie;

import android.view.View;

import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.movie.MvpPresenter;
import com.example.enes.cinemaapp.movie.MvpView;

import java.util.List;

public interface MovieListContract {

    interface MoviePresenter extends MvpPresenter<MovieView> {
        void onGetData(List<Movie> movieList);
        void getMovieList(MoviePresenter moviePresenter,int pageNo);
        void requestDataFromServer();
        void getMoreData(int pageNo);
    }
    interface MovieView extends MvpView {
        void setDataToRecyclerView(List<Movie>movieArrayList);
    }
    interface CastPresenter extends MvpPresenter<CastView>{
        void getDetailList(CastPresenter castPresenter,int movieId);
        void requestMovieData(int movieId);
        void onGetCastData(Movie movie);
    }
    interface CastView extends MvpView{
        void setToView(Movie movie);
    }
}
