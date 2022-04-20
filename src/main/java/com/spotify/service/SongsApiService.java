package com.spotify.service;

import com.spotify.SongsApiDelegate;
import com.spotify.entities.SongEntity;
import com.spotify.model.Song;
import com.spotify.repository.SongRepository;
import com.spotify.utilities.ApiErrorResponse;
import com.spotify.utilities.Constants;
import com.spotify.utilities.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongsApiService implements SongsApiDelegate {

    @Autowired
    SongRepository songRepository;

    @Override
    public ResponseEntity<Object> addSong(Song song) {
        try {
            SongEntity songEntity = ObjectMapperUtils.map(song, SongEntity.class);
            songRepository.save(songEntity);
            return new ResponseEntity<>(songEntity, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getSong(Long songid) {
        try {
            Optional<SongEntity> songEntity = songRepository.findById(songid);
            if (songEntity.isPresent()) {
                return new ResponseEntity<>(songEntity, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, Constants.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getSongs() {
    try {
        List<SongEntity> fetchedAllSongEntityList = (List<SongEntity>) songRepository.findAll();
        List<Song> fetchedAllSongList = ObjectMapperUtils.mapAll(fetchedAllSongEntityList, Song.class);
        return new ResponseEntity<>(fetchedAllSongList, HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @Override
    public ResponseEntity<Object> removeSong(Long songid) {
        try {
            Optional<SongEntity> fetchedSongEntity = songRepository.findById(songid);
            if(fetchedSongEntity.isPresent()) {
                songRepository.deleteById(songid);
                return new ResponseEntity<>(Constants.SONG_DELETE_SUCCESS, HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,Constants.SONG_NOT_FOUND), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,Constants.SONG_DELETE_FAIL + "Error : " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> updateSong(Long songid, Song song) {
        try {
            Optional<SongEntity> fetchedSongEntity = songRepository.findById(songid);
            if(fetchedSongEntity.isPresent()) {
                song.setId(songid);
                SongEntity updatedSong = ObjectMapperUtils.map(song, SongEntity.class);
                songRepository.save(updatedSong);
                return new ResponseEntity<>(song, HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,Constants.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR,Constants.SONG_UPDATE_FAIL + "Error : " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
