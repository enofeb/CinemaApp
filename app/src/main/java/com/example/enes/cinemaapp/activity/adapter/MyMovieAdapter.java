package com.example.enes.cinemaapp.activity.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.enes.cinemaapp.R;
import com.example.enes.cinemaapp.activity.DetailActivity;
import com.example.enes.cinemaapp.data.model.Movie;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.example.enes.cinemaapp.utils.Constants.DETAIL_VIEW;
import static com.example.enes.cinemaapp.utils.Constants.MOVIE_URL;

public class MyMovieAdapter extends RecyclerView.Adapter<MyMovieAdapter.MyViewHolder>  {

    public Context mContext;
    public List<Movie> mMovieList;
    public Movie mSelectedMovie;

    public MyMovieAdapter(Context context, List<Movie> movieList) {
        this.mContext = context;
        this.mMovieList = movieList;
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
        final Movie movie=getItem(position);
        holder.title.setText(mMovieList.get(position).getTitle());
        String vote=Double.toString(mMovieList.get(position).getVoteAverage());
        holder.userVote.setText(vote);
        float starVote=(float)((mMovieList.get(position).getVoteAverage())/2.0);
        holder.rbStarBar.setRating(starVote);

        Glide.with(holder.image.getContext())
                .load(MOVIE_URL+movie.getImagePath()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public Movie getItem(int position){
        if(mMovieList!=null&&mMovieList.size()>0){
            return mMovieList.get(position);
        }
        return null;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.movie_title) TextView title;
        @BindView(R.id.movie_rate) TextView userVote;
        @BindView(R.id.movie_image_2) ImageView image;
        @BindView(R.id.movie_rate_star) RatingBar rbStarBar;


        MyViewHolder(View view){
            super(view);

            ButterKnife.bind(this,view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                        mSelectedMovie=mMovieList.get(pos);
                        Intent i=new Intent(mContext.getApplicationContext(),DetailActivity.class);
                        i.putExtra(DETAIL_VIEW,mSelectedMovie);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(i);
                }
            });
        }
    }
}


