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

public class WritePlaylistServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        createPlaylist(req);
    }

    private void createPlaylist(HttpServletRequest req) throws IOException {
        //get request body as JSON string
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        //convert JSON string to JsonObject
        JsonObject jsonBody = new Gson().fromJson(reqBody, JsonObject.class);
        //initialize firestore repository for access to firestore db
        FirestoreRepository repository = new FirestoreRepository();
        //get data from JSON request body
        String playlistName = jsonBody.get("playlist_name").getAsString();
        //generate data that is going to be put in firestore document
        Map<String, Object> data = new HashMap<>();
        data.put("playlist_name", playlistName);
        //update "last updated" field with current timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        data.put("last_updated", LocalDateTime.now().format(formatter).toString());
        repository.write(playlistName, playlistName, data);
        repository.write("Playlists", playlistName, data);
    }
}
