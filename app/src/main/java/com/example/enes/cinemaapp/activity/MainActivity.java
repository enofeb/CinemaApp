package com.example.enes.cinemaapp.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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


public class MainActivity extends AppCompatActivity implements MovieListContract.MovieView {



    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MoviePresenter moviePresenter;
    private List<Movie> moviesList;
    private MyMovieAdapter myMovieAdapter;
    public MovieListContract.MoviePresenter presenter;

    @Inject
    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((DaggerApp)getApplication()).getAppComponent().inject(this);



        initView();



        //moviePresenter.attachView(this);
        //moviePresenter=new MoviePresenter(service);

        presenter=new MoviePresenter(service);
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

        recyclerView=findViewById(R.id.recycler_view);

        moviesList=new ArrayList<>();
        myMovieAdapter=new MyMovieAdapter(getApplicationContext(),moviesList);


        //Each row has only one movie
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myMovieAdapter);
        recyclerView.smoothScrollToPosition(0);

        swipeRefreshLayout=findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);


    }

}
