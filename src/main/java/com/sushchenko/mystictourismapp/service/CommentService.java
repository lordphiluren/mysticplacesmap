package com.sushchenko.mystictourismapp.service;

import com.sushchenko.mystictourismapp.entity.Comment;
import com.sushchenko.mystictourismapp.entity.Place;
import com.sushchenko.mystictourismapp.entity.User;
import com.sushchenko.mystictourismapp.repo.CommentRepo;
import com.sushchenko.mystictourismapp.utils.exception.CommentNotFoundException;
import com.sushchenko.mystictourismapp.utils.exception.NotEnoughPermissionsException;
import com.sushchenko.mystictourismapp.utils.mapper.CommentMapper;
import com.sushchenko.mystictourismapp.web.dto.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepo commentRepo;
    private final CommentMapper commentMapper;
    private final PlaceService placeService;
    @Transactional
    public Comment addComment(Long placeId, Comment comment, User creator) {
        Place place = placeService.getById(placeId);
        comment.setPlace(place);
        comment.setCreator(creator);
        comment.setCreatedAt(new Date());
        return commentRepo.save(comment);
    }
    @Transactional
    public List<Comment> getCommentsByPlaceId(Long placeId, Integer offset, Integer limit) {
        int offsetValue = offset != null ? offset : 0;
        int limitValue = limit != null ? limit : Integer.MAX_VALUE;
        Pageable pageable = PageRequest.of(offsetValue, limitValue, Sort.by("createdAt"));
        return commentRepo.findByPlaceId(placeId, pageable).getContent();
    }
    @Transactional
    public Comment getCommentById(Long id) {
        return commentRepo.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment with id:" + id + " doesn't exist"));
    }
    @Transactional
    public Comment updateComment(Long id, CommentRequest commentDto, User creator) {
        Comment comment = getCommentById(id);
        if(checkIfCommentCreator(comment.getCreator(), creator)) {
            commentMapper.mergeDtoIntoEntity(commentDto, comment);
            comment = commentRepo.save(comment);
        } else {
            throw new NotEnoughPermissionsException("User with id: " + creator.getId() +
                    " is not allowed to modify comment with id: " + id);
        }
        return comment;
    }
    @Transactional
    public void deleteComment(Long id, User creator) {
        Comment comment = getCommentById(id);
        if(checkIfCommentCreator(comment.getCreator(), creator)) {
            commentRepo.delete(comment);
        } else {
            throw new NotEnoughPermissionsException("User with id: " + creator.getId() +
                    " is not allowed to modify comment with id: " + id);
        }
    }
    private boolean checkIfCommentCreator(User creator, User userPrincipal) {
        return Objects.equals(creator.getId(), userPrincipal.getId());
    }
}
