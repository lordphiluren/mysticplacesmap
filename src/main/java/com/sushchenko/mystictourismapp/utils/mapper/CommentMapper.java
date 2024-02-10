package com.sushchenko.mystictourismapp.utils.mapper;

import com.sushchenko.mystictourismapp.entity.Comment;
import com.sushchenko.mystictourismapp.web.dto.CommentRequest;
import com.sushchenko.mystictourismapp.web.dto.CommentResponse;
import com.sushchenko.mystictourismapp.web.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final ModelMapper modelMapper;

    public CommentResponse toDto(Comment comment) {
        UserResponse userDto = modelMapper.map(comment.getCreator(), UserResponse.class);
        CommentResponse commentDto = modelMapper.map(comment, CommentResponse.class);
        commentDto.setCreator(userDto);
        return commentDto;
    }
    public Comment toEntity(CommentRequest commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }
}
