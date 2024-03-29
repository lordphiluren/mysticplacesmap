package com.sushchenko.mystictourismapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@NamedEntityGraph(
        name = "comment-entity-graph-user_attachs",
        attributeNodes = {
                @NamedAttributeNode("message"),
                @NamedAttributeNode("createdAt"),
                @NamedAttributeNode("creator"),
                @NamedAttributeNode("attachments")
        }
)
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
    private Long id;
    @Column(name = "message", nullable = false)
    private String message;
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment")
    private Set<CommentAttachment> attachments;
}
