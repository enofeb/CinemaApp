package com.example.enes.cinemaapp.View;

import android.content.Context;
import android.widget.ImageView;

import com.example.enes.cinemaapp.Model.Movie;
import com.example.enes.cinemaapp.Model.MovieGetting;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public interface MovieView {

        interface View{
                void setDataToRecyclerView(List<Movie> movieArrayList);

        }

        interface OnGetData{
                void onGetData(List<Movie> movieList);
        }

      //  interface Model{
      //          void getMovieList(OnGetData onGetData);
       // }
        interface Presenter{
                void requestDataFromServer();
                void getMovieList(OnGetData onGetData);
        }

}
