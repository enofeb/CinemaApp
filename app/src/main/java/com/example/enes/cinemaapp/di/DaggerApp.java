package com.example.enes.cinemaapp.di;
import android.app.Application;

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
