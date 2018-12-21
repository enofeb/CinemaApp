package com.example.enes.cinemaapp.activity;

import android.support.annotation.CallSuper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.enes.cinemaapp.dı.DaggerApp;
import com.example.enes.cinemaapp.movie.MovieListContract;
import com.example.enes.cinemaapp.movie.presenter.MoviePresenter;
import com.example.enes.cinemaapp.R;
import com.example.enes.cinemaapp.activity.adapter.MyMovieAdapter;
import com.example.enes.cinemaapp.data.model.Movie;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class MainActivity extends BaseActivity implements MovieListContract.MovieView {

    private List<Movie> moviesList;
    private MyMovieAdapter myMovieAdapter;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;
    private int pageNo = 1;
    private LinearLayoutManager linearLayoutManager;
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
        pageNo++;
    }

    public void initView(){

        moviesList=new ArrayList<>();
        myMovieAdapter=new MyMovieAdapter(getApplicationContext(),moviesList);

        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myMovieAdapter);
        recyclerView.smoothScrollToPosition(0);
        endlessRecyclerView();
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);


    }

    @Override
    protected void endlessRecyclerView() {
        super.endlessRecyclerView();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount=recyclerView.getChildCount();
                totalItemCount=linearLayoutManager.getItemCount();
                firstVisibleItem=linearLayoutManager.findFirstVisibleItemPosition();

                if(loading){
                    if (totalItemCount>previousTotal){
                        loading=false;
                        previousTotal=totalItemCount;
                    }
                }

                if (!loading&&(totalItemCount-visibleItemCount)
                        <=(firstVisibleItem+visibleThreshold)){
                    presenter.getMoreData(pageNo);
                    loading=true;
                }



            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }
}
