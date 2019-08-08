package com.michaelyu.playlisttracker.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Album {
    @SerializedName("artist")
    private String artist;
    @SerializedName("title")
    private String title;
    @SerializedName("url")
    private String url;
    @SerializedName("image")
    private ArrayList<Image> images;

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public ArrayList<Image> getImages() {
        return images;
    }
}
