package com.example.enes.cinemaapp.dı;

import com.example.enes.cinemaapp.movie.MoviePresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {Client.class})
public interface AppComponent {
    void inject(MoviePresenter moviePresenter);
}
