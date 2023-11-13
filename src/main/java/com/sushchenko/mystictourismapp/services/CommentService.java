package com.sushchenko.mystictourismapp.services;

import com.sushchenko.mystictourismapp.entities.Attachment;
import com.sushchenko.mystictourismapp.entities.Comment;
import com.sushchenko.mystictourismapp.entities.Place;
import com.sushchenko.mystictourismapp.entities.User;
import com.sushchenko.mystictourismapp.repos.CommentRepo;
import com.sushchenko.mystictourismapp.repos.PlaceRepo;
import com.sushchenko.mystictourismapp.security.AuthenticationFacade;
import com.sushchenko.mystictourismapp.utils.exceptions.CommentNotFoundException;
import com.sushchenko.mystictourismapp.utils.filemanager.FileManager;
import com.sushchenko.mystictourismapp.web.dto.CommentDTO;
import com.sushchenko.mystictourismapp.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final AuthenticationFacade authFacade;
    private final CommentRepo commentRepo;
    private final FileManager fileManager;
    @Transactional
    public void addComment(Comment comment) {
        comment.setCreator(authFacade.getAuthenticationPrincipal().getUserId());
        comment.setCreatedAt(new Date());
        commentRepo.save(comment);
    }
    @Transactional
    public Comment getById(String id) {
        return commentRepo.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment with id: " + id + " not found"));
    }

    public Comment addCommentAttachments(Comment comment, MultipartFile[] files) {
        if(files.length != 0) {
            String path = fileManager.createCommentsDirectory(comment.getPlaceId());
            List<String> fileUrls = fileManager.saveFiles(files, path);
            comment.setAttachments(fileUrls.stream()
                    .map(Attachment::new)
                    .collect(Collectors.toList()));
        }
        return comment;
    }
    @Transactional
    public void deleteCommentsByPlaceId(String id) {
        List<Comment> comments = commentRepo.findByPlaceId(id);
        comments.forEach(comment -> comment.getAttachments().forEach(att -> fileManager.deleteFile(att.getUrl())));
        commentRepo.deleteByPlaceId(id);
    }
    @Transactional
    public void deleteComment(Comment comment) {
        comment.getAttachments().forEach(att -> fileManager.deleteFile(att.getUrl()));
        commentRepo.delete(comment);
    }
    @Transactional
    public List<Comment> getCommentsByPlaceId(String placeId) {
        return commentRepo.findByPlaceId(placeId);
    }
}
