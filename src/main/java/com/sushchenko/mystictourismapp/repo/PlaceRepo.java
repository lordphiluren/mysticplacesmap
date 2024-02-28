package com.sushchenko.mystictourismapp.repo;

import com.sushchenko.mystictourismapp.entity.Place;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepo extends JpaRepository<Place, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    List<Place> findAll();
    @Query()
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    List<Place> findByTagsIn(List<String> tags);
}
