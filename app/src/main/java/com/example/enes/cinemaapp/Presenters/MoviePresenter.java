package com.example.enes.cinemaapp.Presenters;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.enes.cinemaapp.BuildConfig;
import com.example.enes.cinemaapp.DaggerApp;
import com.example.enes.cinemaapp.Model.Movie;
import com.example.enes.cinemaapp.Model.MovieGetting;
import com.example.enes.cinemaapp.Service;
import com.example.enes.cinemaapp.View.MovieView;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MoviePresenter extends AppCompatActivity implements MovieView.Presenter,MovieView.OnGetData {

    private MovieView.View movieView;
   // private MovieView.Model movieListModel;

    @Inject
    Retrofit retrofit;



    public MoviePresenter(MovieView.View movieView) {
        this.movieView = movieView;
        //movieListModel = new MovieListModel();
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
    public void getMovieList(final MovieView.OnGetData onGetData) {


      //  inject();

        //((DaggerApp)getApplication()).getAppComponent().inject(this);

        Service apiService=retrofit.create(Service.class);

        Call <MovieGetting> call=apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_KEY);




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


 //   public void inject() {
   //     DaggerApp app = (DaggerApp) getApplication();
     //   app.getAppComponent().inject(this);

    //}

}


