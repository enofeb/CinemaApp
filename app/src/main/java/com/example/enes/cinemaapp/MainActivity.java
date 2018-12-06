package com.example.enes.cinemaapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import com.example.enes.cinemaapp.Model.Movie;
import com.example.enes.cinemaapp.Model.MovieGetting;
import com.example.enes.cinemaapp.Presenters.MoviePresenter;
import com.example.enes.cinemaapp.View.MovieView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MovieView.View {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MoviePresenter moviePresenter;
    private List<Movie> moviesList;
    private MyMovieAdapter myMovieAdapter;

    //@Inject
    //Client client;

    @Inject Call<MovieGetting> call;
   // @Inject Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new MoviePresenter(getApplication());

        initView();

        moviePresenter=new MoviePresenter(this);
        //moviePresenter.requestDataFromServer();

        loadJSON();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

               // moviePresenter.requestDataFromServer();
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

        swipeRefreshLayout=findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);


    }


  /**/

        private void loadJSON() {

        try {

            if (BuildConfig.THE_MOVIE_DB_API_KEY.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Empty API KEY", Toast.LENGTH_SHORT).show();
                return;
            }


            inject();

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


            //Client client=new Client();

            //Implement our service interface( we create object from Service Interface)
            //Get API service
            //Service apiService=client.getClient().create(Service.class);

            //Call <MovieGetting> call=client.provideApiService().getPopularMovies(BuildConfig.THE_MOVIE_DB_API_KEY);


        }catch (Exception e){

            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }

        }



        private void inject () {
            DaggerApp app = (DaggerApp) getApplication();
            app.getAppComponent().inject(this);
        }

}
