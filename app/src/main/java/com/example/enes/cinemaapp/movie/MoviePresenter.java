package com.example.enes.cinemaapp.movie;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.data.model.MovieGetting;
import com.example.enes.cinemaapp.dı.DaggerApp;
import com.example.enes.cinemaapp.service.Service;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviePresenter extends AppCompatActivity implements MovieOnGetData {

    private MovieView movieView;


    Service service;

    public MoviePresenter(MovieView movieView,Service service) {
        this.movieView = movieView;
        this.service=service;
    }

    @Override
    public void requestDataFromServer() {

      getMovieList(this);

    }

    @Override
    public void onGetData(List<Movie> movieList) {
        movieView.setDataToRecyclerView(movieList);
    }


    @Override
    public void getMovieList(final MovieOnGetData onGetData) {








            Call <MovieGetting> call=service.getPopularMovies();

            call.enqueue(new Callback<MovieGetting>() {
                @Override
                public void onResponse(Call<MovieGetting> call, Response<MovieGetting> response) {
                    Log.v("RESPONSE_CALLED", "ON_RESPONSE_CALLED");

                    List<Movie> movies=response.body().getResults();
                    onGetData.onGetData(movies);
                }

                @Override
                public void onFailure(Call<MovieGetting> call, Throwable t) {
                    Log.e("error", t.toString());
                }
            });



    }
}


