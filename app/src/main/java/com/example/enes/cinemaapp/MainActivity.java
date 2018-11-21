package com.example.enes.cinemaapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.enes.cinemaapp.Model.Movie;
import com.example.enes.cinemaapp.Model.MovieGetting;

import com.example.enes.cinemaapp.Model.MovieGetting;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyMovieAdapter myMovieAdapter;
    private ArrayList<Movie> movieList;
    private SwipeRefreshLayout swipeRefreshLayout;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editScreen();
        swipeRefreshLayout=findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                editScreen();
                Toast.makeText(MainActivity.this,"Movies Refreshed",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void editScreen(){

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Movies are being refreshed !");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView=findViewById(R.id.recycler_view);


        movieList=new ArrayList<>();
        myMovieAdapter=new MyMovieAdapter(this,movieList);


            //Each row has only one movie
           recyclerView.setLayoutManager(new GridLayoutManager(this,1));


        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myMovieAdapter);
        myMovieAdapter.notifyDataSetChanged();

        loadJSON();

    }

    private void loadJSON(){



        try{

            if (BuildConfig.THE_MOVIE_DB_API_KEY.isEmpty()){
                Toast.makeText(getApplicationContext(),"Empty API KEY",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }

            Client client=new Client();

            //Implement our service interface( we create object from Service Interface)
            //Get API service
            Service apiService=client.getClient().create(Service.class);

            Call <MovieGetting> call=apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_KEY);

            call.enqueue(new Callback<MovieGetting>() {
                @Override
                public void onResponse(Call<MovieGetting> call, Response<MovieGetting> response) {

                   Log.v("RESPONSE_CALLED", "ON_RESPONSE_CALLED");

                    List<Movie> movies=response.body().getResults();


                    recyclerView.setAdapter(new MyMovieAdapter(getApplicationContext(),movies));
                    recyclerView.smoothScrollToPosition(0);

                    if (swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<MovieGetting> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Empty API KEY",Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){

            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }


    }







}
