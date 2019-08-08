package com.michaelyu.playlisttracker.repositories;

import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.michaelyu.playlisttracker.interfaces.LastFmRetrofit;
import com.michaelyu.playlisttracker.models.Track;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class LastFmRepository {
    private String baseUrl = "http://ws.audioscrobbler.com/2.0/";
    private LastFmRetrofit api;
    private String apiKey = System.getenv("LAST_FM_API_KEY");
    private static final String getTrackMethod = "track.getInfo";
    private static final String type = "json";
    public LastFmRepository(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api = retrofit.create(LastFmRetrofit.class);
    }

    public void getTrackInfo(String artist, String track, Callback<Track> callback) throws IOException {
        Call<Track> trackCall = api.getTrack(getTrackMethod, apiKey, artist, track, type);
        trackCall.enqueue(callback);
    }
}
