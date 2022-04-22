package com.spotify.service.song;

import com.spotify.model.Song;
import org.springframework.http.ResponseEntity;

public interface SongsApiService {

    public ResponseEntity<Object> addSong(Song song);

    public ResponseEntity<Object> getSong(Long songid);

    public ResponseEntity<Object> getSongs();

    public ResponseEntity<Object> removeSong(Long songid);

    public ResponseEntity<Object> updateSong(Long songid, Song song);

}