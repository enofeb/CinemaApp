package com.example.enes.cinemaapp.data.database;

import android.content.Context;

import com.example.enes.cinemaapp.data.model.CastGetting;
import com.example.enes.cinemaapp.data.model.Movie;
import java.util.List;
import java.util.concurrent.Callable;


import javax.inject.Singleton;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.RealmResults;


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
            Movie movieSearch=realm.where(Movie.class).equalTo("id",movie.getId()).findFirst();
            if(movieSearch!=null){
                movie.fetchMovie(movieSearch,movie);
            }else {
                movieSearch=realm.createObject(Movie.class,movie.getRealId());
            }
            movie.fetchMovie(movieSearch,movie);
            realm1.insertOrUpdate(movieSearch);
        });
        realm.close();
    }

    @Override
    public void updateMovie(Movie movie, CastGetting castGetting){
        final  Realm realm=Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            Movie movieSearch=realm.where(Movie.class).equalTo("id",movie.getId()).findFirst();
            if (movieSearch.getCasting()==null){
                movie.setCredits(castGetting);
                movie.fetchMovie(movie,movieSearch);
            }
            realm1.insertOrUpdate(movie);
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
    public Observable<Movie> fetchMoviesObservable() { return Observable.fromIterable(fetchMovies()); }

    @Override
    public void clearMovies() {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(transactionRealm -> {
            RealmResults<Movie> result = transactionRealm.where(Movie.class).findAll();
            result.deleteAllFromRealm();
        });
        realm.close();
    }

    @Override
    public Single<Movie> getMovie(long movieId) {
        final Realm realm=Realm.getDefaultInstance();
       return Single.fromCallable(new Callable<Movie>() {
            @Override
            public Movie call() {
                return  realm.where(Movie.class).equalTo("id",movieId).findFirst();
            }
        });
    }
}
