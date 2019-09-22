package com.michaelyu.playlisttracker.models;

import com.google.gson.annotations.SerializedName;

public class TrackInfo {
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    @SerializedName("album")
    private Album album;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Album getAlbum() {
        return album;
    }
}
