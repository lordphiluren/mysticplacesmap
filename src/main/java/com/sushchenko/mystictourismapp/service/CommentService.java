package com.sushchenko.mystictourismapp.service;

import com.sushchenko.mystictourismapp.entity.Comment;
import com.sushchenko.mystictourismapp.entity.CommentAttachment;
import com.sushchenko.mystictourismapp.entity.Place;
import com.sushchenko.mystictourismapp.entity.User;
import com.sushchenko.mystictourismapp.entity.id.CommentAttachmentKey;
import com.sushchenko.mystictourismapp.repo.CommentAttachmentRepo;
import com.sushchenko.mystictourismapp.repo.CommentRepo;
import com.sushchenko.mystictourismapp.service.helper.Helper;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepo commentRepo;
    private final CommentMapper commentMapper;
    private final PlaceService placeService;
    private final CommentAttachmentRepo commentAttachmentRepo;
    private final UploadService uploadService;
    @Transactional
    public Comment addComment(Long placeId, Comment comment, MultipartFile[] attachments, User creator) {
        Place place = placeService.getById(placeId);
        enrichComment(comment, place, creator);
        Comment savedComment = commentRepo.save(comment);
        if(attachments != null) {
            addAttachmentsToComment(savedComment, attachments);
        }
        return savedComment;
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
        if(Helper.checkUserPermissions(comment.getCreator(), creator)) {
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
        if(Helper.checkUserPermissions(comment.getCreator(), creator)) {
            commentRepo.delete(comment);
        } else {
            throw new NotEnoughPermissionsException("User with id: " + creator.getId() +
                    " is not allowed to modify comment with id: " + id);
        }
    }
    @Transactional
    public void addAttachmentsToComment(Comment comment, MultipartFile[] attachments) {
        Set<String> urls = uploadService.uploadAttachments(Arrays.asList(attachments));
        Set<CommentAttachment> attachmentsToSave = new HashSet<>();
        for(String url : urls) {
            CommentAttachmentKey commentAttachmentKey = new CommentAttachmentKey(comment.getId(), url);
            CommentAttachment commentAttachment = new CommentAttachment(commentAttachmentKey, comment);
            attachmentsToSave.add(commentAttachment);
        }
        List<CommentAttachment> savedAttachments = commentAttachmentRepo.saveAll(attachmentsToSave);
        Set<CommentAttachment> commentAttachments = comment.getAttachments();
        commentAttachments.addAll(savedAttachments);
        comment.setAttachments(commentAttachments);
    }
    private void enrichComment(Comment comment, Place place, User creator) {
        comment.setPlace(place);
        comment.setCreator(creator);
        comment.setCreatedAt(new Date());
        comment.setAttachments(new HashSet<>());
    }
}
