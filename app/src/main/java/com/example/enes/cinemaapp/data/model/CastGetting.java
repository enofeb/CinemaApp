package com.example.enes.cinemaapp.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CastGetting {

    @SerializedName("cast")
    private List<Cast> cast;

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }
}
