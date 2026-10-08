package ru.skypro.homework_avito_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework_avito_backend.dto.CommentDto;
import ru.skypro.homework_avito_backend.dto.CommentsDto;
import ru.skypro.homework_avito_backend.dto.CreateOrUpdateCommentDto;
import ru.skypro.homework_avito_backend.service.CommentService;

@RestController
@RequestMapping("/ads")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "Комментарии", description = "Управление комментариями под объявлениями")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}/comments")
    @Operation(summary = "Получение комментариев объявления")
    public ResponseEntity<CommentsDto> getComments(@PathVariable("id") Integer adId) {
        return ResponseEntity.ok(commentService.getComments(adId));
    }

    @PostMapping("/{id}/comments")
    @Operation(summary = "Добавление комментария к объявлению")
    public ResponseEntity<CommentDto> addComment(@PathVariable("id") Integer adId,
                                                 @RequestBody CreateOrUpdateCommentDto text,
                                                 Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.addComment(adId, text, authentication.getName()));
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    @Operation(summary = "Удаление комментария")
    @ApiResponse(responseCode = "204", description = "No Content")
    @PreAuthorize("@adSecurity.isCommentOwnerOrAdmin(#commentId, authentication)")
    public ResponseEntity<Void> deleteComment(@PathVariable("adId") Integer adId,
                                              @PathVariable("commentId") Integer commentId,
                                              Authentication authentication) {
        commentService.deleteComment(adId, commentId, authentication.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    @Operation(summary = "Обновление комментария")
    @PreAuthorize("@adSecurity.isCommentOwnerOrAdmin(#commentId, authentication)")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("adId") Integer adId,
                                                    @PathVariable("commentId") Integer commentId,
                                                    @RequestBody CreateOrUpdateCommentDto text,
                                                    Authentication authentication) {
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, text, authentication.getName()));
    }
}

