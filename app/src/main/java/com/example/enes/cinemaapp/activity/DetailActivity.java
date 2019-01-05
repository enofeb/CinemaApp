package com.example.enes.cinemaapp.activity;
import com.example.enes.cinemaapp.activity.adapter.CastAdapter;
import com.example.enes.cinemaapp.data.DataManagerImp;
import com.example.enes.cinemaapp.data.model.Cast;
import com.example.enes.cinemaapp.data.model.Movie;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.enes.cinemaapp.R;
import com.example.enes.cinemaapp.di.DaggerApp;
import com.example.enes.cinemaapp.movie.contract.DetailContract;
import com.example.enes.cinemaapp.movie.presenter.DetailPresenter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


import static com.example.enes.cinemaapp.utils.Constants.DETAIL_VIEW;
import static com.example.enes.cinemaapp.utils.Constants.MOVIE_URL;

public class DetailActivity extends BaseActivity implements DetailContract.CastView {

    public  Movie mMovie;
    private CastAdapter mCastAdapter;
    private List<Cast> mCastList;

    @BindView(R.id.movie_image) ImageView mIvMovie;
    @BindView(R.id.movie_name) TextView mIvName;
    @BindView(R.id.movie_overview) TextView mIvOverview;
    @BindView(R.id.movie_release_date) TextView mIvReleaseDate;
    @BindView(R.id.movie_vote_average) TextView mIvVoteAvg;
    @BindView(R.id.recycler_view_cast) RecyclerView mRecyclerView;

    @Inject
    DataManagerImp dataManagerImp;

    DetailPresenter mDetailPresenter;

    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DaggerApp)getApplication()).getAppComponent().inject(this);

        mDetailPresenter=new DetailPresenter(dataManagerImp,AndroidSchedulers.mainThread(),Schedulers.io());

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

        if(getIntent().hasExtra(DETAIL_VIEW)){
            mMovie=getIntent().getParcelableExtra(DETAIL_VIEW);
            Glide.with(this).load(MOVIE_URL+mMovie.getImagePath()).into(mIvMovie);
            mIvName.setText(mMovie.getTitle());
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
