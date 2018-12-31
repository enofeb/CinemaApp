package com.example.enes.cinemaapp.di;

import android.content.Context;
import com.example.enes.cinemaapp.BuildConfig;
import com.example.enes.cinemaapp.data.DataManagerImp;
import com.example.enes.cinemaapp.data.database.RealmDatabase;
import com.example.enes.cinemaapp.service.Service;
//import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ServiceModule {

    public   String baseUrl=BuildConfig.BASE_URL;
    public   String apiKey=BuildConfig.THE_MOVIE_DB_API_KEY;
    private Context mContext;


    public ServiceModule(Context context){
        this.mContext=context;
    }

    @Provides
    @Singleton
    String provideBaseUrl(){return baseUrl;}

    @Provides
    @Singleton
    OkHttpClient provideOkHttp(){

        OkHttpClient.Builder okHttpClient=new OkHttpClient.Builder();
        if(BuildConfig.DEBUG){
            okHttpClient.addInterceptor(new HttpLoggingInterceptor());
        }
        okHttpClient.addInterceptor(new KeyInterceptor(apiKey))
                .build();
        return okHttpClient.build();
    }

    @Provides
    @Singleton
    @NonNull
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

    @Provides
    @Singleton
    RealmDatabase provideDataSource(@NonNull Context context){
        return new RealmDatabase(context);
    }

    @Provides
    @Singleton
    DataManagerImp provideDataManager(Service service,RealmDatabase realmDatabase){
        return  new DataManagerImp(service,realmDatabase);
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
