package com.example.enes.cinemaapp.movie.contract;
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
}
