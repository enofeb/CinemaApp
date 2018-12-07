package com.example.enes.cinemaapp.Modules;

import android.util.Log;
import android.widget.Toast;

import com.example.enes.cinemaapp.BuildConfig;
import com.example.enes.cinemaapp.DaggerApp;
import com.example.enes.cinemaapp.MainActivity;
import com.example.enes.cinemaapp.Model.Movie;
import com.example.enes.cinemaapp.Model.MovieGetting;
import com.example.enes.cinemaapp.Service;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class Client {

    //Singleton final class
    //It is our API URL
    public  static final String BASE_URL="https://api.themoviedb.org/3/";
    private final DaggerApp daggerApp;

    public Client(DaggerApp daggerApp){
        this.daggerApp=daggerApp;
    }


    @Provides @Singleton
    DaggerApp provideApp(){
        return daggerApp;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(){
        return new Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL).build();
    }

    @Provides
    Call<MovieGetting> provideMovieList(Retrofit retrofit){
        return retrofit.create(Service.class).getPopularMovies(BuildConfig.THE_MOVIE_DB_API_KEY);
    }


   /* @Provides
    @Singleton
    public Service provideApiService(){
        Retrofit retrofit=new Retrofit.Builder().
                    baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create())
                    .build();
        return retrofit.create(Service.class);
    }*/


    // Get Retrofit instance
    /*public static Retrofit getClient(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder().
                    baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }*/

}
