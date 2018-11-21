package com.example.enes.cinemaapp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;


public class DetailActivity extends AppCompatActivity {

    public TextView movieName,movieOverView,movieReleaseDate,movieUserAverage;
    public ImageView imageView;

    private StorageReference storageRef=FirebaseStorage.getInstance().getReference("Uploaded Movie Posters");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_content_detail_layout);

        imageView=findViewById(R.id.movie_image);
        movieName=findViewById(R.id.movie_name);
        movieOverView=findViewById(R.id.movie_overview);
        movieReleaseDate=findViewById(R.id.movie_release_date);
        movieUserAverage=findViewById(R.id.movie_vote_average);

        Intent comenIntent=getIntent();

        if(comenIntent.hasExtra("original_title")){

            String nameOfMovie=getIntent().getExtras().getString("original_title");
            String overView=getIntent().getExtras().getString("overview");
            String vote=getIntent().getExtras().getString("vote_average");
            String releaseDate=getIntent().getExtras().getString("release_date");
            String image=getIntent().getExtras().getString("poster_path");


            //Here,we upload the movie poster which clicked ,upload firebase
            Glide.with(this).load(image).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                               DataSource dataSource, boolean isFirstResource) {


                    String a=URLUtil.guessFileName(getIntent().getExtras().getString("poster_path"),null,null);


                    StorageReference uploadedImageRef = storageRef.child(a);



                    imageView.setDrawingCacheEnabled(true);
                    imageView.buildDrawingCache();

                    Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask =  uploadedImageRef.putBytes(data);

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.v("WARNING","warninggg");
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.v("SUCCESS","SUCCESS");
                        }
                    });


                            return false;
                    }
                    }).into(imageView);

            movieName.setText(nameOfMovie);
            movieOverView.setText(overView);
            movieUserAverage.setText(vote);
            movieReleaseDate.setText(releaseDate);

        }
    }

}
