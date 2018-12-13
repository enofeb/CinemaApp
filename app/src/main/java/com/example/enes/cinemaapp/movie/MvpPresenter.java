package com.example.enes.cinemaapp.movie;

public interface MvpPresenter<M extends MvpView> {

    void attachView(M view);

}
