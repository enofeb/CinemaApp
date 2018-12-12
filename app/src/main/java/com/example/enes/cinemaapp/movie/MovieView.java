package com.example.enes.cinemaapp.movie;

import com.example.enes.cinemaapp.data.model.Movie;

import java.util.List;

public interface MovieView {

    void setDataToRecyclerView(List<Movie> movieArrayList);

}
