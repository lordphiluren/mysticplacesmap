package com.sushchenko.mystictourismapp.utils.mapper;

import com.sushchenko.mystictourismapp.entity.Comment;
import com.sushchenko.mystictourismapp.web.dto.CommentRequest;
import com.sushchenko.mystictourismapp.web.dto.CommentResponse;
import com.sushchenko.mystictourismapp.web.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final ModelMapper modelMapper;

    public CommentResponse toDto(Comment comment) {
        UserResponse userDto = modelMapper.map(comment.getCreator(), UserResponse.class);
        Set<String> attachmentsDto = comment.getAttachments().stream()
                .map(attach -> attach.getId().getUrl())
                .collect(Collectors.toSet());
        CommentResponse commentDto = modelMapper.map(comment, CommentResponse.class);
        commentDto.setAttachments(attachmentsDto);
        commentDto.setCreator(userDto);
        return commentDto;
    }
    public Comment toEntity(CommentRequest commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }
    public void mergeDtoIntoEntity(CommentRequest commentDto, Comment comment) {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(commentDto, comment);
    }
}
