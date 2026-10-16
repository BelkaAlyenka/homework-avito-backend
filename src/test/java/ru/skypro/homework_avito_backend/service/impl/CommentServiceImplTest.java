package ru.skypro.homework_avito_backend.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework_avito_backend.dto.CommentDto;
import ru.skypro.homework_avito_backend.dto.CommentsDto;
import ru.skypro.homework_avito_backend.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework_avito_backend.mapper.CommentMapper;
import ru.skypro.homework_avito_backend.model.Ad;
import ru.skypro.homework_avito_backend.model.Comment;
import ru.skypro.homework_avito_backend.model.User;
import ru.skypro.homework_avito_backend.repository.AdRepository;
import ru.skypro.homework_avito_backend.repository.CommentRepository;
import ru.skypro.homework_avito_backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private AdRepository adRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void getComments_Success() {
        Integer adId = 1;
        Comment comment = new Comment();
        CommentDto commentDto = new CommentDto();
        CommentsDto expectedCommentsDto = new CommentsDto();
        expectedCommentsDto.setCount(1);
        expectedCommentsDto.setResults(new ArrayList<>(Collections.singletonList(commentDto)));

        when(commentRepository.findAllByAdId(adId)).thenReturn(Collections.singletonList(comment));
        when(commentMapper.toCommentDto(comment)).thenReturn(commentDto);

        CommentsDto result = commentService.getComments(adId);

        assertNotNull(result);
        assertEquals(1, result.getCount());
        assertEquals(1, result.getResults().size());
    }

    @Test
    void addComment_Success() {
        Integer adId = 1;
        String username = "user@gmail.com";
        CreateOrUpdateCommentDto textDto = new CreateOrUpdateCommentDto();
        textDto.setText("Отличный товар!");

        Ad ad = new Ad();
        User author = new User();
        Comment comment = new Comment();
        CommentDto expectedDto = new CommentDto();

        when(adRepository.findById(adId)).thenReturn(Optional.of(ad));
        when(userRepository.findByEmail(username)).thenReturn(Optional.of(author));
        when(commentMapper.toEntity(textDto)).thenReturn(comment);
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(commentMapper.toCommentDto(any(Comment.class))).thenReturn(expectedDto);

        CommentDto result = commentService.addComment(adId, textDto, username);

        assertNotNull(result);

        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository, times(1)).save(commentCaptor.capture());
        assertNotNull(commentCaptor.getValue().getCreatedAt());
    }

    @Test
    void deleteComment_Success() {
        Integer adId = 1;
        Integer commentId = 1;

        Ad ad = new Ad();
        ad.setId(adId);

        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setAd(ad);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        commentService.deleteComment(adId, commentId, "user@gmail.com");

        verify(commentRepository, times(1)).delete(comment);
    }
}

