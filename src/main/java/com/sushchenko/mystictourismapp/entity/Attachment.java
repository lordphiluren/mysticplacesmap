package com.sushchenko.mystictourismapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Attachment")
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
            name = "Comment_Attachment",
            joinColumns = { @JoinColumn(name = "attachment_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "comment_id", referencedColumnName = "id") }
    )
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Place_Attachment",
            joinColumns = { @JoinColumn(name = "attachment_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "place_id", referencedColumnName = "id") }
    )
    private List<Place> place;
}
