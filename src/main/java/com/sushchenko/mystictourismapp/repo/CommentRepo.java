package com.sushchenko.mystictourismapp.repo;

import com.sushchenko.mystictourismapp.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "comment-entity-graph-user_attachs")
    Page<Comment> findByPlaceId(Long placeId, Pageable pageable);
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "comment-entity-graph-user_attachs")
    Optional<Comment> findById(Long id);
}
