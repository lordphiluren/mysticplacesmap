package com.sushchenko.mystictourismapp.utils.mappers;

import ch.qos.logback.core.model.Model;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sushchenko.mystictourismapp.entities.Comment;
import com.sushchenko.mystictourismapp.services.UserService;
import com.sushchenko.mystictourismapp.web.dto.CommentDTO;
import com.sushchenko.mystictourismapp.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final ModelMapper modelMapper;
    private final UserService userService;

    public CommentDTO mapToCommentDTO(Comment comment) {
        UserDTO userDTO = modelMapper.map(userService.getById(comment.getCreator()), UserDTO.class);
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
        commentDTO.setCreator(userDTO);
        return commentDTO;
    }
    public Comment mapToComment(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }
}
