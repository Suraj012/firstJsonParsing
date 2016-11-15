package com.example.user.design1.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 4/28/2016.
 */
public class MovieModel {
    private String movie, duration, director, image, story;
    private int year;
    private float rating;
    @SerializedName("cast")
    private List<cast> castlist;

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<cast> getCastlist() {
        return castlist;
    }

    public void setCastlist(List<cast> castlist) {
        this.castlist = castlist;
    }

    public static class cast{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
