package com.example.enes.cinemaapp.di;
import android.content.Context;
import com.example.enes.cinemaapp.data.DataManagerImp;
import com.example.enes.cinemaapp.movie.presenter.DetailPresenter;
import com.example.enes.cinemaapp.movie.presenter.MoviePresenter;
import javax.inject.Named;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class PresenterModule {

    public Context mContext;

    public PresenterModule(Context context){this.mContext=context;}

    @Provides
    Context provideContext(){return  this.mContext;}

    @Provides
    @Singleton
    @Named("main")
    Scheduler provideMainScheduler(){return AndroidSchedulers.mainThread();}

    @Provides
    @Singleton
    @Named("io")
    Scheduler provideIoScheduler(){return Schedulers.io();}

    @Provides
    MoviePresenter provideMoviePresenter(DataManagerImp dataManager,@Named("main") Scheduler mainScheduler,
                                         @Named("io") Scheduler ioScheduler){
        return new MoviePresenter(dataManager,mainScheduler,ioScheduler);
    }

    @Provides
    DetailPresenter provideDetailPresenter(DataManagerImp dataManager, @Named("main") Scheduler mainScheduler,
                                          @Named("io") Scheduler ioScheduler){
        return new DetailPresenter(dataManager,mainScheduler,ioScheduler);
    }


}
