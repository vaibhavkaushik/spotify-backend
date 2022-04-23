package com.spotify.security;

public enum ApplicationUserPermission {
    SONG_READ("song:read"),
    SONG_WRITE("song:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }
}
