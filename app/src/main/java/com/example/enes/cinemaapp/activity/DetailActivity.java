package com.example.enes.cinemaapp.activity;
import com.bumptech.glide.Glide;
import com.example.enes.cinemaapp.activity.adapter.CastAdapter;
import com.example.enes.cinemaapp.data.model.Cast;
import com.example.enes.cinemaapp.data.model.Movie;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.enes.cinemaapp.R;
import com.example.enes.cinemaapp.DaggerApp;
import com.example.enes.cinemaapp.movie.contract.DetailContract;
import com.example.enes.cinemaapp.movie.presenter.DetailPresenter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import static com.example.enes.cinemaapp.utils.Constants.DETAIL_VIEW;
import static com.example.enes.cinemaapp.utils.Constants.MOVIE_URL;

public class DetailActivity extends BaseActivity implements DetailContract.CastView {

    public  Movie mMovie;
    private CastAdapter mCastAdapter;
    private List<Cast> mCastList;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.movie_image) ImageView mIvMovie;
    @BindView(R.id.movie_overview) TextView mIvOverview;
    @BindView(R.id.movie_release_date) TextView mIvReleaseDate;
    @BindView(R.id.movie_vote_average) TextView mIvVoteAvg;
    @BindView(R.id.recycler_view_cast) RecyclerView mRecyclerView;

    @Inject
    DetailPresenter mDetailPresenter;

    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DaggerApp)getApplication()).getAppComponent().inject(this);

        mDetailPresenter.attachView(this);
        mDetailPresenter.requestMovieData(mMovie.getId());
    }

    @Override
    public void setToView(Movie movie) {
        mCastList.addAll(movie.getCasting().getCast());
        mCastAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {

        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.YELLOW);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.YELLOW);

        if(getIntent().hasExtra(DETAIL_VIEW)){
            mMovie=getIntent().getParcelableExtra(DETAIL_VIEW);
            Glide.with(this).load(MOVIE_URL+mMovie.getImagePath()).into(mIvMovie);

            collapsingToolbarLayout.setTitle(mMovie.getTitle());
            mIvOverview.setText(mMovie.getOverView());
            mIvVoteAvg.setText(mMovie.getVoteAverage().toString());
            mIvReleaseDate.setText(mMovie.getReleaseDate());
            mCastList=new ArrayList<>();
            mCastAdapter=new CastAdapter(this,mCastList);
            mRecyclerView.setAdapter(mCastAdapter);
        }


    }
    @Override
    protected int getContentView() {
        return R.layout.movie_content_detail_layout;
    }

}
