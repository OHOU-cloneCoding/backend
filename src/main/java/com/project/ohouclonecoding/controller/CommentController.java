package com.project.ohouclonecoding.controller;

import com.project.ohouclonecoding.dto.CommentRequestDto;
import com.project.ohouclonecoding.dto.CommentResponseDto;
import com.project.ohouclonecoding.dto.CreateCommentRequestDto;
import com.project.ohouclonecoding.entity.Comment;
import com.project.ohouclonecoding.entity.User;
import com.project.ohouclonecoding.security.UserDetailsImpl;
import com.project.ohouclonecoding.service.CommentService;
import jakarta.servlet.ServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Transactional
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{postId}")
    public ResponseEntity<CommentResponseDto<List<Comment>>> createComment(ServletResponse servletResponse, @PathVariable("postId") Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody(required = true) Optional<CreateCommentRequestDto> createCommentRequestDto) throws RuntimeException {
        servletResponse.setBufferSize(819200);
        if(!createCommentRequestDto.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        CommentResponseDto<List<Comment>> commentResponseDto = commentService.createComment(postId, userDetails, createCommentRequestDto.get());
        return new ResponseEntity<CommentResponseDto<List<Comment>>>(commentResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable("commentId") Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody Optional<CreateCommentRequestDto> createCommentRequestDto) {
        if(!createCommentRequestDto.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Boolean isValidCommentWriter = commentService.isValidComment(userDetails, commentId);
        if(isValidCommentWriter == false) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//댓글이 존재하지 않거나 올바른 작성자가 아닙니다.
        Boolean isRemoved = commentService.updateComment(commentId, createCommentRequestDto.get());
        if(isRemoved) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> removeComment(@PathVariable("commentId") Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Boolean isValidCommentWriter = commentService.isValidComment(userDetails, commentId);
        if(isValidCommentWriter == false) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//댓글이 존재하지 않거나 올바른 작성자가 아닙니다.
        Boolean isRemoved = commentService.removeComment(commentId);
        if(isRemoved) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
