package com.example.enes.cinemaapp.dı;

import com.example.enes.cinemaapp.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {Client.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
