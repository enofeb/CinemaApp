package com.example.enes.cinemaapp.di;
import com.example.enes.cinemaapp.activity.DetailActivity;
import com.example.enes.cinemaapp.activity.MainActivity;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {ServiceModule.class,PresenterModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(DetailActivity detailActivity);
}
