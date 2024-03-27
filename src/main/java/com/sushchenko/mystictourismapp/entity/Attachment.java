package com.sushchenko.mystictourismapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attachment")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "url", unique = true, nullable = false)
    private String url;

    // Relations
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "comment_attachment",
            joinColumns = { @JoinColumn(name = "attachment_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "comment_id", referencedColumnName = "id") }
    )
    private Set<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "place_attachment",
            joinColumns = { @JoinColumn(name = "attachment_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "place_id", referencedColumnName = "id") }
    )
    private Set<Place> place;

    public Attachment(String url) {
        this.url = url;
    }
}
