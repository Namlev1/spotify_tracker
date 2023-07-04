package com.spotifytracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Entity(name = "_user")
@Data
@NoArgsConstructor
public class User implements UserDetails, OAuth2User {

    @Id
    private String id;
    private String displayName;
    private String href;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;
    private String type;
    private String uri;
    private int followers;

    // TODO better implementation
    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return displayName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
