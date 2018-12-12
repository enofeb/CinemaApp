package com.example.enes.cinemaapp.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Movie;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity {

    public static  String ARG_PARAM="param1";
    public  Movie movie_;
    public String c;

    @BindView(R.id.movie_image) ImageView imageView;
    @BindView(R.id.movie_name) TextView movieName;
    @BindView(R.id.movie_overview) TextView movieOverView;
    @BindView(R.id.movie_release_date) TextView movieReleaseDate;
    @BindView(R.id.movie_vote_average) TextView movieUserAverage;

    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_content_detail_layout);

        ButterKnife.bind(this);

        //Intent comeIntent=getIntent();

        movie_=getIntent().getParcelableExtra(ARG_PARAM);

        


        if(getIntent().hasExtra(ARG_PARAM)){





            String nameOfMovie=getIntent().getExtras().getString("original_title");
            String overView=getIntent().getExtras().getString("overview");
            String vote=getIntent().getExtras().getString("vote_average");
            String releaseDate=getIntent().getExtras().getString("release_date");
            String image=getIntent().getExtras().getString("poster_path");

            Glide.with(this).load(image).into(imageView);

            movieName.setText(nameOfMovie);
            movieOverView.setText(overView);
            movieUserAverage.setText(vote);
            movieReleaseDate.setText(releaseDate);

        }
    }

}
