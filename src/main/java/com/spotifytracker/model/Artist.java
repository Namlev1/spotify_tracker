package com.spotifytracker.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {
    @Id
    private String id;
    private String name;
    private int followers;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;
    @ManyToMany(mappedBy = "artists")
    @JsonBackReference
    List<User> users;

    public Artist(String id, String name, int followers, List<Image> images) {
        this.id = id;
        this.name = name;
        this.followers = followers;
        this.images = images;
    }
}
