package com.example.enes.cinemaapp.dı;

import android.content.Context;

import com.example.enes.cinemaapp.movie.MovieListContract;
import com.example.enes.cinemaapp.movie.presenter.MoviePresenter;
import com.example.enes.cinemaapp.service.Service;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    public Context context;

    public PresenterModule(Context context){this.context=context;}

@Provides
    MoviePresenter provideMoviePresenter(Service service){
        return  new MoviePresenter(service);
    }
}
