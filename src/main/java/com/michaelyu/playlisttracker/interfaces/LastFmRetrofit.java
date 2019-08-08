package com.michaelyu.playlisttracker.interfaces;

import com.michaelyu.playlisttracker.models.Track;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface LastFmRetrofit {
    @GET(".")
    Call<Track> getTrack(@Query("method") String method, @Query("api_key") String apiKey, @Query("artist") String artist, @Query("track") String track, @Query("format") String type);
}
