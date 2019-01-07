package com.example.enes.cinemaapp;
import android.app.Application;

import com.example.enes.cinemaapp.di.AppComponent;
import com.example.enes.cinemaapp.di.DaggerAppComponent;
import com.example.enes.cinemaapp.di.PresenterModule;
import com.example.enes.cinemaapp.di.ServiceModule;

public class DaggerApp extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent=DaggerAppComponent.builder().
                serviceModule(new ServiceModule(this)).
                presenterModule(new PresenterModule(this)).build();

    }

    public  AppComponent getAppComponent(){return mAppComponent;}
}
