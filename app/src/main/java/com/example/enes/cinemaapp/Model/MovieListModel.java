package com.example.enes.cinemaapp.Model;

import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class MovieListModel extends AppCompatActivity {

    @Inject
    Retrofit retrofit;


   /* @Override
    public void getMovieList(final MovieView.OnGetData onGetData) {



       // try{
         //  if (BuildConfig.THE_MOVIE_DB_API_KEY.isEmpty()){
           //     Toast.makeText(getApplicationContext(),"Empty API KEY",Toast.LENGTH_SHORT).show();
             //   return;
           // }

        //inject();
        ((DaggerApp2)getApplication()).getAppComponent2().inject(this);
        //Client2 client=new Client2();

        //Implement our service interface( we create object from Service Interface)
        //Get API service
        Service apiService=retrofit.create(Service.class);

        Call <MovieGetting> call=apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_KEY);




            call.enqueue(new Callback<MovieGetting>() {
                @Override
                public void onResponse(Call<MovieGetting> call, Response<MovieGetting> response) {
                    Log.v("RESPONSE_CALLED", "ON_RESPONSE_CALLED");

                    List<Movie> movies=response.body().getResults();
                    onGetData.onGetData(movies);
                }

                @Override
                public void onFailure(Call<MovieGetting> call, Throwable t) {
                    Log.e("error", t.toString());
                }
            });


       // }catch (Exception e){
         //   Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
       // }

    }

   public void inject(){
        DaggerApp2 app=(DaggerApp2) getApplication();
           app.getAppComponent2().inject(this);
    }*/


}
