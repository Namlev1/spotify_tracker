package com.spotifytracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@Entity(name = "_user")
@Data
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    private String id;
    private String displayName;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_uris", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "uri")
    private List<URI> externalUrls;
    private URI href;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_images")
    private List<Image> images;
    private String type;
    private URI uri;
    private int followers;

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
}
