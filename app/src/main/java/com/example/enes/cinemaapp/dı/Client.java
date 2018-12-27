package com.example.enes.cinemaapp.dı;

import android.content.Context;
import com.example.enes.cinemaapp.BuildConfig;
import com.example.enes.cinemaapp.service.Service;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.io.IOException;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import io.reactivex.annotations.NonNull;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

@Module
public class Client {


    public  static final String BASE_URL="https://api.themoviedb.org/3/";
    public   String apiKey=BuildConfig.THE_MOVIE_DB_API_KEY;
    private Context context;

    public Client(Context context){
        this.context=context;
    }


    @Provides
    @Singleton
    String provideBaseUrl(){return BASE_URL;}

    @Provides
    @Singleton
    OkHttpClient provideOkHttp(){
        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(new KeyInterceptor(apiKey))
                .build();
        return client;
    }

    @Provides
    @Singleton
    Retrofit provideRetroFit(@NonNull String baseUrl, OkHttpClient client){

        return new Retrofit.Builder()
                .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    Service provideService(Retrofit retrofit){
      return  retrofit.create(Service.class);
    }

    public class KeyInterceptor implements Interceptor{

        private String iApiKey;

        public KeyInterceptor(String iApiKey){
            this.iApiKey=iApiKey;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            HttpUrl url=chain.request().url()
                    .newBuilder()
                    .addQueryParameter("api_key",iApiKey)
                    .build();

            Request request=chain.request().newBuilder().url(url).build();

            return chain.proceed(request);
        }
    }


}
