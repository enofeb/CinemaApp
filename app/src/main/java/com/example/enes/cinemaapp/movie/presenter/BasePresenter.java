package com.example.enes.cinemaapp.movie.presenter;
import com.example.enes.cinemaapp.movie.MvpPresenter;
import com.example.enes.cinemaapp.movie.MvpView;

public class BasePresenter<T extends MvpView> implements MvpPresenter<T> {

    public T mView;

    @Override
    public void attachView(T view) {
        this.mView=view;
    }

}
