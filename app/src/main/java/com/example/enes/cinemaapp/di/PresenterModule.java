package com.example.enes.cinemaapp.di;
import android.content.Context;
import com.example.enes.cinemaapp.movie.presenter.MoviePresenter;
import com.example.enes.cinemaapp.service.Service;
import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    public Context mContext;

    public PresenterModule(Context context){this.mContext=context;}

    @Provides
    Context provideContext(){return  this.mContext;}
}
