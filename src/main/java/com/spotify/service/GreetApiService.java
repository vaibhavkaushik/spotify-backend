package com.spotify.service;

import com.spotify.GreetApiDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GreetApiService implements GreetApiDelegate {
    @Override
    public ResponseEntity<String> greetUser() {

        return ResponseEntity.ok("Hello Nidhi !!");
    }
}
