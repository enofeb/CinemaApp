package com.example.enes.cinemaapp.data;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.enes.cinemaapp.data.database.IDatabase;
import com.example.enes.cinemaapp.data.model.Movie;
import com.example.enes.cinemaapp.data.model.MovieGetting;
import com.example.enes.cinemaapp.service.Service;
import java.util.List;
import java.util.UUID;
import javax.inject.Singleton;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import static com.example.enes.cinemaapp.utils.Constants.CREDITS;

@Singleton
public class DataManagerImp implements DataManager {

    private static final String TAG=DataManagerImp.class.getName();

    @NonNull
    private final   Service mService;

    @NonNull
    private final IDatabase mIDatabase;

    public DataManagerImp(@NonNull Service mService, @NonNull IDatabase mIDatabase) {
        this.mService = mService;
        this.mIDatabase = mIDatabase;
    }

    @Override
    public Single<List<Movie>> getMovies(@Nullable Integer page) {

        Observable<Movie> movieObservable=mService.getPopularMovies(page)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(movieMovieGetting -> clearMovies(movieMovieGetting))
                .map(movieMovieGetting -> movieMovieGetting.getResults())
                .toObservable()
                .flatMap(movieList -> Observable.fromIterable(movieList))
                .doOnNext(movie -> Log.i("DataManager",movie.toString()))
                .doOnNext(movie -> {
                    String realId=UUID.randomUUID().toString();
                    movie.setRealId(realId);
                    mIDatabase.saveMovie(movie);

                });

        return movieObservable.toList();
    }

    @Override
    public Single<Movie> getCast(@Nullable Integer movieId, @Nullable String credits) {

        Single<Movie> movieObservable=mService.getMovieCredits(movieId,CREDITS)
                            .subscribeOn(Schedulers.io())
                            .doOnSuccess(movie -> mIDatabase.updateMovie(movie))
                            .doOnSuccess(movie -> Log.i("CASTING",movie.toString()));
        return movieObservable;
    }


    @Override
    public Maybe<List<Movie>> getMoviesFromLocal() {
        //Kaydolan veriler ve serviceden gelen veriler kaydedlip birleştirilyr
        return Single.concat(mIDatabase.fetchMoviesObservable().toList(),getMovies(null))
                .filter(movieList -> movieList!=null&&movieList.size()>0).firstElement();
    }

    @Override
   public Maybe<Movie> getCastFromLocal(Integer movieId){
        return Single.concat(mIDatabase.fetchCastSingle(movieId),getCast(movieId,"credits"))
                .filter(movie -> movie.getCasting()!=null).firstElement();
    }

    private void clearMovies(MovieGetting<Movie> movieDiscoverResponse) {
        if (movieDiscoverResponse.getPage() == 1) {
            mIDatabase.clearMovies();
        }
    }
}
