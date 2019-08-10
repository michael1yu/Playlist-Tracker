package com.michaelyu.playlisttracker.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.michaelyu.playlisttracker.repositories.FirestoreRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        String playlistName = jsonBody.get("playlist_name").getAsString();
        String trackName = jsonBody.get("track_name").getAsString();
        String artist = jsonBody.get("artist").getAsString();

        Map<String, Object> data = new HashMap<>();
        data.put("is_track", true);
        data.put("track_name", trackName);
        data.put("artist", artist);
        //writes song doc to playlist name collection
        firestoreRepository.write(playlistName, trackName, data);
        //writes playlist doc to playlist collection
        writePlaylistTimestamp(firestoreRepository, playlistName);
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
