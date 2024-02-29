package com.sushchenko.mystictourismapp.repo;

import com.sushchenko.mystictourismapp.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;

@Repository
public interface PlaceRepo extends JpaRepository<Place, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    List<Place> findAll();
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    Page<Place> findAll(Pageable pageable);
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    @Query()
    List<Place> findByTagsIn(Set<String> tags);
}
