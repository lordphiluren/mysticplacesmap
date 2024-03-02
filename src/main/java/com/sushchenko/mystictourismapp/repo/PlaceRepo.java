package com.sushchenko.mystictourismapp.repo;

import com.sushchenko.mystictourismapp.entity.Place;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PlaceRepo extends JpaRepository<Place, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    List<Place> findAll(Sort sort);
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    Page<Place> findAll(Pageable pageable);
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    Optional<Place> findById(Long id);
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    @Query("SELECT p " +
            "FROM Place p " +
            "LEFT JOIN p.tags pt " +
            "LEFT JOIN p.creator u " +
            "LEFT JOIN p.attachments a " +
            "WHERE pt.id.tag IN :tags")
    List<Place> findByTagsIn(@Param("tags") Set<String> tags);
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    @Query("SELECT p " +
            "FROM Place p " +
            "LEFT JOIN p.tags pt " +
            "LEFT JOIN p.creator u " +
            "LEFT JOIN p.attachments a " +
            "WHERE pt.id.tag IN :tags")
    Page<Place> findByTagsIn(@Param("tags") Set<String> tags, Pageable pageable);
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    List<Place> findAllByRating(Double rating);
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    Page<Place> findAllByRating(Double rating, Pageable pageable);
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    @Query("SELECT p " +
            "FROM Place p " +
            "LEFT JOIN p.tags pt " +
            "LEFT JOIN p.creator u " +
            "LEFT JOIN p.attachments a " +
            "WHERE pt.id.tag IN :tags AND p.rating = :rating")
    List<Place> findByTagsAndRating(@Param("tags") Set<String> tags, @Param("rating") Double rating);
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    @Query("SELECT p " +
            "FROM Place p " +
            "LEFT JOIN p.tags pt " +
            "LEFT JOIN p.creator u " +
            "LEFT JOIN p.attachments a " +
            "WHERE pt.id.tag IN :tags AND p.rating = :rating")
    Page<Place> findByTagsAndRating(@Param("tags") Set<String> tags, @Param("rating") Double rating, Pageable pageable);
}
