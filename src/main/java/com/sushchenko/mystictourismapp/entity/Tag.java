package com.sushchenko.mystictourismapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    // Relations
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "place_tag",
            joinColumns = { @JoinColumn(name = "tag_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "place_id", referencedColumnName = "id") }
    )
    private List<Place> places;
}
