package com.michaelyu.playlisttracker.servlets;

import com.google.gson.JsonObject;
import com.michaelyu.playlisttracker.repositories.FirestoreRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;

public class GetPlaylistServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //set JSON response
        try {
            JsonObject response = getPlaylist(req);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
            PrintWriter out = resp.getWriter();
            out.println(response.toString());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //access firestore repository and query tracks from playlist with name {playlist_name}
    private JsonObject getPlaylist(HttpServletRequest req) throws ExecutionException, InterruptedException {
        String playlistName = req.getParameter("playlist_name");
        FirestoreRepository firestoreRepository = new FirestoreRepository();
        return firestoreRepository.queryTracks(playlistName);
    }
}
