package com.example.enes.cinemaapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    //It is our API URL
    public  static final String BASE_URL="https://api.themoviedb.org/3/";
    public static Retrofit retrofit=null;

    // Get Retrofit instance
    public static Retrofit getClient(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder().
                    baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
