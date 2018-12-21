package com.example.enes.cinemaapp.movie.presenter;

import com.example.enes.cinemaapp.movie.MvpPresenter;
import com.example.enes.cinemaapp.movie.MvpView;

public class BasePresenter<T extends MvpView> implements MvpPresenter<T> {

    public T view;

    @Override
    public void attachView(T view) {
        this.view=view;
    }


}
