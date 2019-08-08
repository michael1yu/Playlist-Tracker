package com.michaelyu.playlisttracker.models;

import com.google.gson.annotations.SerializedName;

public class Track {
    @SerializedName("track")
    private TrackInfo trackinfo;

    public TrackInfo getTrackinfo() {
        return trackinfo;
    }
}
