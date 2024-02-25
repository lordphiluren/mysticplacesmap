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
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;
    @Column(name = "message", nullable = false)
    private String message;
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Relations
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "comment_attachment",
            joinColumns = { @JoinColumn(name = "comment_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "attachment_id", referencedColumnName = "id") }
    )
    private List<Attachment> attachments;
}
