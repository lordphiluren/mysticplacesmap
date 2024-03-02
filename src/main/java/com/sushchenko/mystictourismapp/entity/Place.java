package com.sushchenko.mystictourismapp.entity;

import com.sushchenko.mystictourismapp.entity.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@NamedEntityGraph(
    name = "place-entity-graph-user_tags_attachs_comments",
    attributeNodes = {
        @NamedAttributeNode("name"),
        @NamedAttributeNode("shortDescription"),
        @NamedAttributeNode("fullDescription"),
        @NamedAttributeNode("howToGet"),
        @NamedAttributeNode("address"),
        @NamedAttributeNode("rating"),
        @NamedAttributeNode("latitude"),
        @NamedAttributeNode("longitude"),
        @NamedAttributeNode("status"),
        @NamedAttributeNode("createdAt"),
        @NamedAttributeNode("creator"),
        @NamedAttributeNode("tags"),
        @NamedAttributeNode("attachments")
    }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "place")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "short_description")
    private String shortDescription;
    @Column(name = "full_description")
    private String fullDescription;
    @Column(name = "how_to_get")
    private String howToGet;
    @Column(name = "address")
    private String address;
    @Column(name = "rating")
    @ColumnDefault("0")
    private Double rating;
    @Column(name = "latitude", nullable = false)
    private Double latitude;
    @Column(name = "longitude", nullable = false)
    private Double longitude;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User creator;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    private Set<PlaceTag> tags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    private Set<PlaceRating> placeRates;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    private Set<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "place_attachment",
            joinColumns = { @JoinColumn(name = "place_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "attachment_id", referencedColumnName = "id") }
    )
    private Set<Attachment> attachments;
}
