package com.example.enes.cinemaapp.movie.presenter;

import android.util.Log;

import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.data.model.MovieGetting;
import com.example.enes.cinemaapp.movie.MovieListContract;
import com.example.enes.cinemaapp.service.Service;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviePresenter extends BasePresenter<MovieListContract.MovieView> implements MovieListContract.MoviePresenter {

    public Service service;

    public MoviePresenter(Service service) {
        this.service=service;

    }

    @Override
    public void requestDataFromServer() {
      getMovieList(this);
    }

    @Override
    public void onGetData(List<Movie> movieList) {
        view.setDataToRecyclerView(movieList);
    }

    @Override
    public void getMovieList(final MovieListContract.MoviePresenter movieListContract) {

            Call <MovieGetting> call=service.getPopularMovies();

            call.enqueue(new Callback<MovieGetting>() {
                @Override
                public void onResponse(Call<MovieGetting> call, Response<MovieGetting> response) {
                    Log.v("RESPONSE_CALLED", "ON_RESPONSE_CALLED");

                    List<Movie> movies=response.body().getResults();
                    movieListContract.onGetData(movies);
                }

                @Override
                public void onFailure(Call<MovieGetting> call, Throwable t) {
                    Log.e("error", t.toString());
                }
            });

    }

}


