package com.example.enes.cinemaapp.activity;
import android.support.annotation.CallSuper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.enes.cinemaapp.DaggerApp;
import com.example.enes.cinemaapp.movie.contract.MovieListContract;
import com.example.enes.cinemaapp.movie.presenter.MoviePresenter;
import com.example.enes.cinemaapp.R;
import com.example.enes.cinemaapp.activity.adapter.MyMovieAdapter;
import com.example.enes.cinemaapp.data.model.Movie;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements MovieListContract.MovieView {

    private List<Movie> mMovieList;
    private MyMovieAdapter mMovieAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;
    private int pageNo = 1;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout mSwipeLayout;

    @Inject
    MoviePresenter presenter;

    @CallSuper
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((DaggerApp)getApplication()).getAppComponent().inject(this);

        presenter.attachView(this);

        presenter.requestDataFromServer();

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                presenter.requestDataFromServer();

                if (mSwipeLayout.isRefreshing()){
                    mSwipeLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void setDataToRecyclerView(List<Movie> movieArrayList) {
        mMovieList.addAll(movieArrayList);
        mMovieAdapter.notifyDataSetChanged();
        pageNo++;
    }

    public void initView(){

        mMovieList=new ArrayList<>();
        mMovieAdapter=new MyMovieAdapter(getApplicationContext(),mMovieList);
        mLinearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mMovieAdapter);
        mRecyclerView.smoothScrollToPosition(0);
        endlessRecyclerView();
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_orange_dark);

    }

    protected void endlessRecyclerView() {


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount=recyclerView.getChildCount();
                totalItemCount=mLinearLayoutManager.getItemCount();
                firstVisibleItem=mLinearLayoutManager.findFirstVisibleItemPosition();

                if(loading){
                    if (totalItemCount>previousTotal){
                        loading=false;
                        previousTotal=totalItemCount;
                    }
                }
                if (!loading&&(totalItemCount-visibleItemCount)
                        <=(firstVisibleItem+visibleThreshold)){
                    presenter.getMoreData(pageNo);
                    loading=true;
                }
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }
}
