package com.spotify.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

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

    public Set<SimpleGrantedAuthority> getGrantedAuthorities () {

        Set<SimpleGrantedAuthority>   permissions = getPermissions().stream()
                 .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                 .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return permissions;
    }
}
