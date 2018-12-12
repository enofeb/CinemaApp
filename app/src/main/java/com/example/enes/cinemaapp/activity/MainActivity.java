package com.example.enes.cinemaapp.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import com.example.enes.cinemaapp.BuildConfig;
import com.example.enes.cinemaapp.movie.MoviePresenter;
import com.example.enes.cinemaapp.R;
import com.example.enes.cinemaapp.movie.MovieView;
import com.example.enes.cinemaapp.activity.adapter.MyMovieAdapter;
import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.data.model.MovieGetting;
import com.example.enes.cinemaapp.service.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MovieView {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MoviePresenter moviePresenter;
    private List<Movie> moviesList;
    private MyMovieAdapter myMovieAdapter;

    //@Inject
    //Client client;

    //@Inject Call<MovieGetting> call;
   // @Inject Retrofit retrofit;


    @Inject
    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        initView();

        moviePresenter=new MoviePresenter(this,service);

        moviePresenter.requestDataFromServer();

       // loadJSON();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                moviePresenter.requestDataFromServer();
                //loadJSON();
                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }


                //Toast.makeText(MainActivity.this,"Movies Refreshed",Toast.LENGTH_SHORT).show();
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

    private void loadJSON() {

        try {

            if (BuildConfig.THE_MOVIE_DB_API_KEY.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Empty API KEY", Toast.LENGTH_SHORT).show();
                return;
            }





           // Client client=new Client();

            //Implement our service interface( we create object from Service Interface)
            //Get API service
           // Service apiService=client.getClient().create(Service.class);

           // ((DaggerApp)getApplication()).getAppComponent().inject(this);

            Call <MovieGetting> call=service.getPopularMovies();

            call.enqueue(new Callback<MovieGetting>() {
                @Override
                public void onResponse(Call<MovieGetting> call, Response<MovieGetting> response) {
                    Log.v("RESPONSE_CALLED", "ON_RESPONSE_CALLED");

                    List<Movie> movies = response.body().getResults();

                    recyclerView.setAdapter(new MyMovieAdapter(getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);

                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<MovieGetting> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Empty API KEY", Toast.LENGTH_SHORT).show();
                }
            });





        }catch (Exception e){

            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }

        }


}
