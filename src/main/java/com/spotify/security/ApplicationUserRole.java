package com.spotify.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.spotify.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {

    ADMIN(Sets.newHashSet(USER_READ, USER_WRITE, SONG_READ, SONG_WRITE)),
    USER(Sets.newHashSet(USER_READ, USER_WRITE, SONG_READ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
