package com.example.enes.cinemaapp.dı;

import android.app.Application;
import com.example.enes.cinemaapp.dı.DaggerAppComponent;

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
