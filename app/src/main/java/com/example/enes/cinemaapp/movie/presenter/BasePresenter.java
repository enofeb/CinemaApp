package com.example.enes.cinemaapp.movie.presenter;
import com.example.enes.cinemaapp.movie.MvpPresenter;
import com.example.enes.cinemaapp.movie.MvpView;

import io.reactivex.disposables.CompositeDisposable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<T extends MvpView> implements MvpPresenter<T> {

    public T mView;
    public CompositeDisposable mCompositeDisposable;
    public CompositeSubscription compositeSubscription;

    @Override
    public void attachView(T view) {
        this.mView=view;
        compositeSubscription=new CompositeSubscription();
        //mCompositeDisposable=new CompositeDisposable();
    }
    //MemoryLeak
    @Override
    public void detachView() {
       compositeSubscription.clear();
       compositeSubscription=null;
       mView=null;
    }

    public T getView(){return mView;}

    public void addSubscription(Subscription subscription){
        compositeSubscription.add(subscription);
    }
}
