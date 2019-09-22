# Playlist-Tracker API

# Description
This API serves the PlaylistTracker app. It receives a list of playlists and songs from the app and records them in a Google Firestore Database.

# Endpoints

The API site is https://playlist-tracker.herokuapp.com/api

/get_playlist - (params: playlist_name) (returns list of all songs in playlist)

/write_playlist - (body: playlist_name)

/write_track - (body: playlist_name, track_name, artist)



