package com.sushchenko.mystictourismapp.services;

import com.sushchenko.mystictourismapp.entities.Comment;
import com.sushchenko.mystictourismapp.repos.CommentRepo;
import com.sushchenko.mystictourismapp.utils.filemanager.CommentFileManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentRepo commentRepo;
    @Mock
    private CommentFileManager commentFileManager;
    @BeforeEach
    void setUp() {
    }

    @Test
    void giveComment_whenAddComment_thenCommentIsAddedWithCurrentDate() {
        // given
        Comment comment = new Comment();

        // when
        commentService.addComment(comment);

        // then
        verify(commentRepo, times(1)).save(comment);
        assertNotNull(comment.getCreatedAt());
    }

    @Test
    void giveCommentId_whenGetById_thenReturnComment() {
        // given
        Comment expected = Comment.builder().id("123").build();
        when(commentRepo.findById(anyString())).thenReturn(Optional.of(expected));

        // when
        Comment actual = commentService.getById("123");

        // then
        Assertions.assertEquals(expected, actual);
        verify(commentRepo, times(1)).findById(anyString());
        verifyNoMoreInteractions(commentRepo);
    }

    @Test
    void addCommentAttachments() {
    }

    @Test
    void givePlaceId_whenDeleteCommentsByPlaceId_thenAllPlaceRelatedCommentsDeleted() {
        // given
        List<Comment> comments = new ArrayList<>();
        Comment comment1 = Comment.builder().id("1").build();
        Comment comment2 = Comment.builder().id("2").build();
        comments.add(comment1);
        comments.add(comment2);
        when(commentRepo.findByPlaceId(anyString())).thenReturn(comments);

        // when
        commentService.deleteCommentsByPlaceId(anyString());

        // then
        verify(commentFileManager, times(1)).deleteCommentsAttachments(comment1);
        verify(commentFileManager, times(1)).deleteCommentsAttachments(comment2);
        verify(commentRepo, times(1)).deleteByPlaceId(anyString());
    }

    @Test
    void giveComment_whenDeleteComment_thenCommentAndAttachmentsDeleted() {
        // given
        doNothing().when(commentRepo).delete(new Comment());

        // when
        commentService.deleteComment(new Comment());

        // then
        verify(commentRepo, times(1)).delete(new Comment());
        verify(commentFileManager, times(1)).deleteCommentsAttachments(new Comment());
        verifyNoMoreInteractions(commentRepo);
        verifyNoMoreInteractions(commentFileManager);
    }

    @Test
    void givePlaceId_whenGetCommentsByPlaceId_thenReturnListOfComments() {
        // given
        List<Comment> expected =List.of(new Comment());
        when(commentRepo.findByPlaceId(anyString())).thenReturn(expected);

        // when
        List<Comment> actual = commentService.getCommentsByPlaceId(anyString());

        // then
        Assertions.assertIterableEquals(expected, actual);
        verify(commentRepo, times(1)).findByPlaceId(anyString());
        verifyNoMoreInteractions(commentRepo);
    }
}