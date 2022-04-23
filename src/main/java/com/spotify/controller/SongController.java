package com.spotify.controller;

import com.spotify.SongsApi;
import com.spotify.model.Song;
import com.spotify.service.song.SongsApiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SongController implements SongsApi {

    @Autowired
    SongsApiServiceImpl songsApiServiceImpl;

    @Override
    public ResponseEntity<Object> addSong(Song song) {
        return songsApiServiceImpl.addSong(song);
    }

    @Override
    public ResponseEntity<Object> getSong(Long songid) {
        return songsApiServiceImpl.getSong(songid);
    }

    @Override
    public ResponseEntity<Object> getSongs() {
        return songsApiServiceImpl.getSongs();
    }

    @Override
    public ResponseEntity<Object> removeSong(Long songid) {
        return songsApiServiceImpl.removeSong(songid);
    }

    @Override
    public ResponseEntity<Object> updateSong(Long songid, Song song) {
        return songsApiServiceImpl.updateSong(songid, song);
    }
}
