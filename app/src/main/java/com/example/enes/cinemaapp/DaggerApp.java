package com.example.enes.cinemaapp;

import android.app.Application;

import com.example.enes.cinemaapp.Modules.Client;
import com.example.enes.cinemaapp.Components.AppComponent;
import com.example.enes.cinemaapp.Components.DaggerAppComponent;

public class DaggerApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        test();
    }

    private void test(){
        appComponent=DaggerAppComponent.builder().
                client(new Client(this)).build();
    }

    public  AppComponent getAppComponent(){return appComponent;}
}
