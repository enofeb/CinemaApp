package com.example.enes.cinemaapp.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class CastGetting implements RealmModel {

    @SerializedName("cast")
    private RealmList<Cast> cast;

    public RealmList<Cast> getCast() {
        return cast;
    }

    public void setCast(RealmList<Cast> cast) {
        this.cast = cast;
    }
}
