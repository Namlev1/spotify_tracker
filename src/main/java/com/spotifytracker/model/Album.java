package com.spotifytracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    @Id
    private String id;
    private String name;
    private LocalDate releaseDate;
    private String type;
    private String uri;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;
}
