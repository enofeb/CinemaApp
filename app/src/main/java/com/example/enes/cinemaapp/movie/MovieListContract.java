package com.example.enes.cinemaapp.movie;

import android.view.View;

import com.example.enes.cinemaapp.data.model.Movie;

import java.util.List;

public interface MovieListContract {

    interface MoviePresenter extends MvpPresenter<MovieView>{
        void onGetData(List<Movie> movieList);
        void getMovieList(MoviePresenter moviePresenter);
        void requestDataFromServer();
    }
    interface MovieView extends MvpView{
        void setDataToRecyclerView(List<Movie>movieArrayList);
    }
}
