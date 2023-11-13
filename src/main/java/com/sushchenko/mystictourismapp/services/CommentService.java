package com.sushchenko.mystictourismapp.services;

import com.sushchenko.mystictourismapp.entities.Comment;
import com.sushchenko.mystictourismapp.entities.Place;
import com.sushchenko.mystictourismapp.entities.User;
import com.sushchenko.mystictourismapp.repos.CommentRepo;
import com.sushchenko.mystictourismapp.repos.PlaceRepo;
import com.sushchenko.mystictourismapp.security.AuthenticationFacade;
import com.sushchenko.mystictourismapp.web.dto.CommentDTO;
import com.sushchenko.mystictourismapp.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final AuthenticationFacade authFacade;
    private final CommentRepo commentRepo;

    @Transactional
    public void addComment(String placeId, Comment comment) {
        comment.setPlaceId(placeId);
        comment.setCreator(authFacade.getAuthenticationPrincipal().getUserId());
        comment.setCreatedAt(new Date());
        commentRepo.save(comment);
    }
    @Transactional
    public List<Comment> getCommentsByPlaceId(String placeId) {
        return commentRepo.findByPlaceId(placeId);
    }
}
