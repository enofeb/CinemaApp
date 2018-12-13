package com.example.enes.cinemaapp.movie.presenter;

import com.example.enes.cinemaapp.movie.MovieListContract;
import com.example.enes.cinemaapp.movie.MvpPresenter;
import com.example.enes.cinemaapp.movie.MvpView;
import com.example.enes.cinemaapp.service.Service;

public class BasePresenter<T extends MvpView> implements MvpPresenter<T> {

    public T view;

    @Override
    public void attachView(T view) {
        this.view=view;
    }
}
