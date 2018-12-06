package com.example.enes.cinemaapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.enes.cinemaapp.Model.Movie;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyMovieAdapter extends RecyclerView.Adapter<MyMovieAdapter.MyViewHolder>  {

    private Context context;
    private List<Movie> mList;
    public static String movieId;

    private DatabaseReference dRef= FirebaseDatabase.getInstance().getReference();
    private DatabaseReference rootRef=dRef.child("user");




    public MyMovieAdapter(Context context, List<Movie> mList) {
        this.context = context;
        this.mList = mList;
    }


    @NonNull
    @Override
    public MyMovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.movie_layout,parent,false);

        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) { //final

        holder.title.setText(mList.get(position).getTitle());
        String vote=Double.toString(mList.get(position).getVoteAverage());
        holder.userVote.setText(vote);


        Glide.with(context).load(mList.get(position).getImagePath()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView title,userVote;
        public ImageView image;



        MyViewHolder(View view){
            super(view);
            title=view.findViewById(R.id.movie_title);
            userVote=view.findViewById(R.id.movie_rate);
            image=view.findViewById(R.id.movie_image_2);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos=getAdapterPosition();
                    //if line
                    Movie selectedMovie=mList.get(pos);
                    Intent i=new Intent(context,DetailActivity.class);
                    i.putExtra("original_title",mList.get(pos).getTitle());
                    i.putExtra("poster_path",mList.get(pos).getImagePath());
                    i.putExtra("overview",mList.get(pos).getOverView());
                    i.putExtra("vote_average",Double.toString(mList.get(pos).getVoteAverage()));
                    i.putExtra("release_date",mList.get(pos).getReleaseDate());
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    movieId=dRef.push().getKey();

                    rootRef.child(movieId).child("movieName").setValue(mList.get(pos).getTitle());
                    rootRef.child(movieId).child("movieImage").setValue(mList.get(pos).getImagePath());


                    context.startActivity(i);


                }
            });

        }





    }



}


