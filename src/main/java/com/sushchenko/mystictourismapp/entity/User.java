package com.sushchenko.mystictourismapp.entity;

import com.sushchenko.mystictourismapp.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    // Relations
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
    private List<Place> places;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
    private List<PlaceRating> placeRatings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
    private List<Comment> comments;
}
