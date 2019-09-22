package com.michaelyu.playlisttracker.models;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("#text")
    private String url;
    @SerializedName("size")
    private String size;

    public String getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }
}
