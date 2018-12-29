package com.example.enes.cinemaapp.data.database;

import android.content.Context;
import com.example.enes.cinemaapp.data.model.Movie;
import java.util.List;
import javax.inject.Singleton;

import io.realm.RealmResults;
import rx.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Singleton
public class RealmDatabase implements IDatabase {

    private Context mContext;
    private RealmConfiguration mRealConfiguration;

    public RealmDatabase(){}

    public RealmDatabase(Context context){
        this.mContext=context;
        Realm.init(this.mContext);
        this.mRealConfiguration=new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(mRealConfiguration);
    }

    @Override
    public void saveMovie(Movie movie) {
        final Realm realm=Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {

            final Movie realMovie=realm.createObject(Movie.class,movie.getRealId());

            movie.fetchMovie(realMovie,movie);
        });
        realm.close();
    }

    @Override
    public List<Movie> fetchMovies() {
        final Realm realm=Realm.getDefaultInstance();
        List<Movie> allMovies=realm.copyFromRealm(realm.where(Movie.class).findAll());
        realm.close();
        return allMovies;
    }

    @Override
    public Observable<Movie> fetchMoviesObservable() {
        return Observable.from(fetchMovies());
    }


    @Override
    public void clearMovies() {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(transactionRealm -> {
            RealmResults<Movie> result = transactionRealm.where(Movie.class).findAll();
            result.deleteAllFromRealm();
        });
        realm.close();
    }
}
