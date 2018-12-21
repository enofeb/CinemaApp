package com.example.enes.cinemaapp.activity;


import android.content.Intent;
import android.graphics.Bitmap;

import com.example.enes.cinemaapp.activity.adapter.CastAdapter;
import com.example.enes.cinemaapp.data.model.Cast;
import com.example.enes.cinemaapp.data.model.Movie;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.enes.cinemaapp.R;
import com.example.enes.cinemaapp.dı.DaggerApp;
import com.example.enes.cinemaapp.movie.MovieListContract;
import com.example.enes.cinemaapp.movie.presenter.DetailPresenter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends BaseActivity implements MovieListContract.CastView {

    public static  String ARG_PARAM="param1";
    public  Movie movie_;
    private CastAdapter castAdapter;
    private List<Cast> castList;

    @BindView(R.id.movie_image) ImageView imageView;
    @BindView(R.id.movie_name) TextView movieName;
    @BindView(R.id.movie_overview) TextView movieOverView;
    @BindView(R.id.movie_release_date) TextView movieReleaseDate;
    @BindView(R.id.movie_vote_average) TextView movieUserAverage;
    @BindView(R.id.recycler_view_cast) RecyclerView recyclerView;

    @Inject
    DetailPresenter detailPresenter;


    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DaggerApp)getApplication()).getAppComponent().inject(this);

        detailPresenter.attachView(this);
        detailPresenter.requestMovieData(movie_.getId());


    }

    @Override
    public void setToView(Movie movie) {
        castList.addAll(movie.getCasting().getCast());
        castAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {

        if(getIntent().hasExtra(ARG_PARAM)){
            movie_=getIntent().getParcelableExtra(ARG_PARAM);
            Glide.with(this).load(movie_.getImagePath()).into(imageView);
            movieName.setText(movie_.getTitle());
            movieOverView.setText(movie_.getOverView());
            movieUserAverage.setText(movie_.getVoteAverage().toString());
            movieReleaseDate.setText(movie_.getReleaseDate());
            castList=new ArrayList<>();
            castAdapter=new CastAdapter(this,castList);
            recyclerView.setAdapter(castAdapter);

            //castList.addAll(movie_.getCasting().getCast());



        }




    }

    @Override
    protected int getContentView() {
        return R.layout.movie_content_detail_layout;
    }

}
