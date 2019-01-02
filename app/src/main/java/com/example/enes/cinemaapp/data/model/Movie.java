package com.example.enes.cinemaapp.data.model;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class Movie extends RealmObject  implements Parcelable {

        @PrimaryKey
        @SerializedName("realm _id")
        private String realId;

        @SerializedName("id")
        private Integer id;

        @SerializedName("title")
        private String title;

        @SerializedName("overview")
        private String overView;

        @SerializedName("release_date")
        private String releaseDate;

        @SerializedName("vote_count")
        private Integer voteCount;

        @SerializedName("poster_path")
        private String imagePath;

        @SerializedName("adult")
        private Boolean adult;

        @SerializedName("video")
        private Boolean video;

        @SerializedName("vote_average")
        private Double voteAverage;

        @SerializedName("popularity")
        private Double popularity;

        @SerializedName("original_language")
        private String originalLanguage;

        @SerializedName("original_title")
        private String originalTitle;

        @SerializedName("backdrop_path")
        private String backdropPath;

        @SerializedName("credits")
        private CastGetting castGetting;

        public Movie(){}

        public Movie(Integer id, String title, String overView, String releaseDate, Integer voteCount,
                 String imagePath, Boolean adult, Boolean video, List<Integer> genreIds, Double voteAverage,
                 Double popularity, String originalLanguage, String originalTitle, String backdropPath) {
        this.id = id;
        this.title = title;
        this.overView = overView;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.imagePath = imagePath;
        this.adult = adult;
        this.video = video;
        //this.genreIds = genreIds;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.backdropPath = backdropPath;
    }

    public String getRealId() {
        return realId;
    }

    public void setRealId(String realId) {
        this.realId = realId;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }


    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public CastGetting getCasting(){return  castGetting;}

    public void setCredits(CastGetting castGetting) {
        this.castGetting =castGetting;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(overView);
        dest.writeString(releaseDate);
        dest.writeInt(voteCount);
        dest.writeString(imagePath);
        dest.writeValue(adult);
        dest.writeValue(video);
        dest.writeDouble(voteAverage);
        dest.writeDouble(popularity);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeString(backdropPath);
    }

    public Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        overView = in.readString();
        releaseDate = in.readString();
        voteCount = in.readInt();
        imagePath = in.readString();
        adult = (Boolean) in.readValue(Boolean.class.getClassLoader());
        video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        voteAverage = in.readDouble();
        popularity = in.readDouble();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        backdropPath = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public void fetchMovie(Movie movie,Movie realMovie){
        movie.setId(realMovie.getId());
        movie.setTitle(realMovie.getTitle());
        movie.setOverView(realMovie.getOverView());
        movie.setReleaseDate(realMovie.getReleaseDate());
        movie.setVoteCount(realMovie.getVoteCount());
        movie.setImagePath(realMovie.getImagePath());
        movie.setAdult(realMovie.getAdult());
        movie.setVideo(realMovie.getVideo());
        movie.setVoteAverage(realMovie.getVoteAverage());
        movie.setPopularity(realMovie.getPopularity());
        movie.setOriginalLanguage(realMovie.getOriginalLanguage());
        movie.setOriginalTitle(realMovie.getTitle());
        movie.setBackdropPath(realMovie.getBackdropPath());
        //movie.setCredits(realMovie.getCasting());
    }

    @Override
    public String toString() {
        return "Movie{" +
                "realId='" + realId + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", overView='" + overView + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", voteCount=" + voteCount +
                ", imagePath='" + imagePath + '\'' +
                ", adult=" + adult +
                ", video=" + video +
                ", voteAverage=" + voteAverage +
                ", popularity=" + popularity +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", castGetting=" + castGetting +
                '}';
    }
}
