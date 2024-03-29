package com.sushchenko.mystictourismapp.repo;

import com.sushchenko.mystictourismapp.entity.Place;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PlaceRepo extends JpaRepository<Place, Long>, JpaSpecificationExecutor<Place> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    List<Place> findAll(Sort sort);
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    Page<Place> findAll(Pageable pageable);
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    Optional<Place> findById(Long id);
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "place-entity-graph-user_tags_attachs_comments")
    Page<Place> findAll(Specification<Place> spec, Pageable pageable);
}
