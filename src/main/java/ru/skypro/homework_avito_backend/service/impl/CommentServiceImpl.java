package ru.skypro.homework_avito_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import ru.skypro.homework_avito_backend.service.CommentService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentsDto getComments(Integer adId) {
        List<CommentDto> commentDtoList = commentRepository.findAllByAdId(adId).stream()
                .map(commentMapper::toCommentDto)
                .collect(Collectors.toList());

        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setCount(commentDtoList.size());
        commentsDto.setResults(commentDtoList);
        return commentsDto;
    }

    @Override
    @Transactional
    public CommentDto addComment(Integer adId, CreateOrUpdateCommentDto text, String username) {
        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new NoSuchElementException("Объявление не найдено с id: " + adId));

        User author = userRepository.findByEmail(username)
                .orElseThrow(() -> new NoSuchElementException("Пользователь не найден: " + username));

        Comment comment = commentMapper.toEntity(text);
        comment.setAd(ad);
        comment.setAuthor(author);
        comment.setCreatedAt(System.currentTimeMillis());

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toCommentDto(savedComment);
    }

    @Override
    @Transactional
    public void deleteComment(Integer adId, Integer commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Комментарий не найден с id: " + commentId));

        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public CommentDto updateComment(Integer adId, Integer commentId, CreateOrUpdateCommentDto text, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Комментарий не найден с id: " + commentId));

        comment.setText(text.getText());
        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toCommentDto(updatedComment);
    }
}
