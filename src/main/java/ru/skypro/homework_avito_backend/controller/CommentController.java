package ru.skypro.homework_avito_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework_avito_backend.dto.CommentDto;
import ru.skypro.homework_avito_backend.dto.CommentsDto;
import ru.skypro.homework_avito_backend.dto.CreateOrUpdateCommentDto;

import java.util.Collections;

@RestController
@RequestMapping("/ads")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "Комментарии", description = "Управление комментариями под объявлениями")
public class CommentController {

    private static final Integer DEFAULT_COMMENT_ID = 1;
    private static final Integer EMPTY_COUNT = 0;

    @GetMapping("/{id}/comments")
    @Operation(summary = "Получение комментариев объявления")
    public ResponseEntity<CommentsDto> getComments(@PathVariable("id") Integer adId) {
        CommentsDto comments = new CommentsDto();
        comments.setCount(EMPTY_COUNT);
        comments.setResults(Collections.emptyList());
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{id}/comments")
    @Operation(summary = "Добавление комментария к объявлению")
    public ResponseEntity<CommentDto> addComment(@PathVariable("id") Integer adId,
                                                 @RequestBody CreateOrUpdateCommentDto text) {
        CommentDto createdComment = new CommentDto();
        createdComment.setPk(DEFAULT_COMMENT_ID);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    @Operation(summary = "Удаление комментария")
    @ApiResponse(responseCode = "204", description = "No Content")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer adId,
                                              @PathVariable Integer commentId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    @Operation(summary = "Обновление комментария")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Integer adId,
                                                    @PathVariable Integer commentId,
                                                    @RequestBody CreateOrUpdateCommentDto text) {
        CommentDto updatedComment = new CommentDto();
        updatedComment.setPk(commentId);
        return ResponseEntity.ok(updatedComment);
    }
}
