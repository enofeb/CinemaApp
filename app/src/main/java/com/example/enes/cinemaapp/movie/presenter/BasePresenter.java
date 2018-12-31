package com.example.enes.cinemaapp.movie.presenter;
import com.example.enes.cinemaapp.movie.MvpPresenter;
import com.example.enes.cinemaapp.movie.MvpView;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<T extends MvpView> implements MvpPresenter<T> {

    public T mView;
    public CompositeDisposable mCompositeDisposable;


    @Override
    public void attachView(T view) {
        this.mView=view;
        mCompositeDisposable=new CompositeDisposable();
    }
    //MemoryLeak
    @Override
    public void detachView() {

       mView=null;
    }

    public T getView(){return mView;}


}
