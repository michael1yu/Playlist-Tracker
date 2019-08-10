package com.michaelyu.playlisttracker.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.michaelyu.playlisttracker.models.Album;
import com.michaelyu.playlisttracker.models.Image;
import com.michaelyu.playlisttracker.models.Track;
import com.michaelyu.playlisttracker.models.TrackInfo;
import com.michaelyu.playlisttracker.repositories.FirestoreRepository;
import com.michaelyu.playlisttracker.repositories.LastFmRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WriteTrackServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        writeTrack(req);
    }

    private void writeTrack(HttpServletRequest req) throws IOException {
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JsonObject jsonBody = new Gson().fromJson(reqBody, JsonObject.class);
        //initializes FirestoreRepository to get access to firestore database
        FirestoreRepository firestoreRepository = new FirestoreRepository();
        //initializes LastFmRepository to get access to LastFm API
        LastFmRepository lastFmRepository = new LastFmRepository();
        String playlistName = jsonBody.get("playlist_name").getAsString();
        String trackName = jsonBody.get("track_name").getAsString();
        String artist = jsonBody.get("artist").getAsString();

        //Passes artist, track, and Callback to lastfm repository
        lastFmRepository.getTrackInfo(artist, trackName, new Callback<Track>() {
            //What happens if lastfm call is successful
            @Override
            public void onResponse(Call<Track> call, Response<Track> response) {
                Map<String, Object> data = new HashMap<>();
                data.put("is_track", true);
                data.put("track_name", trackName);
                data.put("artist", artist);
                Track track = response.body();
                TrackInfo trackInfo = track.getTrackinfo();
                //lastfm has found the track
//                if (trackInfo != null) {
//                    Album album = trackInfo.getAlbum();
//                    //lastfm has found the album
//                    if (album != null) {
//                        ArrayList<Image> images = album.getImages();
//                        data.put("album", album.getTitle());
//                        //iterate through array album art images and set field with size description
//                        for (Image image : images) {
//                            data.put("album_art_" + image.getSize(), image.getUrl());
//                        }
//                    }
//                }

                //writes song doc to playlist name collection
                firestoreRepository.write(playlistName, trackName, data);
                //writes playlist doc to playlist collection
                writePlaylistTimestamp(firestoreRepository, playlistName);
            }

            //if lastfm API call fails
            @Override
            public void onFailure(Call<Track> call, Throwable throwable) {
                //still write basic data
                Map<String, Object> data = new HashMap<>();
                data.put("is_track", true);
                data.put("track_name", trackName);
                data.put("artist", artist);


                firestoreRepository.write(playlistName, trackName, data);
                writePlaylistTimestamp(firestoreRepository, playlistName);

                //print out error message
                System.out.println(throwable.getMessage());
            }
        });
    }

    //writes timestamp of when the write occurred
    private void writePlaylistTimestamp(FirestoreRepository firestoreRepository, String playlistName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Map<String, Object> data = new HashMap<>();
        data.put("last_updated", LocalDateTime.now().format(formatter).toString());
        firestoreRepository.write(playlistName, playlistName, data);
        firestoreRepository.write("Playlists", playlistName, data);
    }
}
