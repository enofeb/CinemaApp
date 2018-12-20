package com.example.enes.cinemaapp.activity;

import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.enes.cinemaapp.dı.DaggerApp;
import com.example.enes.cinemaapp.movie.MovieListContract;
import com.example.enes.cinemaapp.movie.presenter.MoviePresenter;
import com.example.enes.cinemaapp.R;
import com.example.enes.cinemaapp.activity.adapter.MyMovieAdapter;
import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.service.Service;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements MovieListContract.MovieView {

    private List<Movie> moviesList;
    private MyMovieAdapter myMovieAdapter;
    //public MovieListContract.MoviePresenter presenter;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;


    @Inject
    MoviePresenter presenter;

    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        ((DaggerApp)getApplication()).getAppComponent().inject(this);


        //  presenter=new MoviePresenter(service);
        presenter.attachView(this);


        presenter.requestDataFromServer();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                presenter.requestDataFromServer();

                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        });

    }


    @Override
    public void setDataToRecyclerView(List<Movie> movieArrayList) {
        moviesList.addAll(movieArrayList);
        myMovieAdapter.notifyDataSetChanged();
    }

    public void initView(){

        moviesList=new ArrayList<>();
        myMovieAdapter=new MyMovieAdapter(getApplicationContext(),moviesList);

        //Each row has only one movie
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myMovieAdapter);
        recyclerView.smoothScrollToPosition(0);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);


    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }
}
