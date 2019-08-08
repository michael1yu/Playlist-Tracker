package com.michaelyu.playlisttracker.servlets;

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

public class WritePlaylistServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        createPlaylist(req);
    }

    private void createPlaylist(HttpServletRequest req){
        FirestoreRepository repository = new FirestoreRepository();
        String playlistName = req.getParameter("playlist_name");
        Map<String, Object> data = new HashMap<>();
        data.put("playlist_name", playlistName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        data.put("last_updated", LocalDateTime.now().format(formatter).toString());
        repository.write(playlistName, playlistName, data);
        repository.write("Playlists", playlistName, data);
    }
}
