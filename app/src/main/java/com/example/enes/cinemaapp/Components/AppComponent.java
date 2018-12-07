package com.example.enes.cinemaapp.Components;

import com.example.enes.cinemaapp.Model.MovieListModel;
import com.example.enes.cinemaapp.Modules.Client;
import com.example.enes.cinemaapp.MainActivity;
import com.example.enes.cinemaapp.Presenters.MoviePresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {Client.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
