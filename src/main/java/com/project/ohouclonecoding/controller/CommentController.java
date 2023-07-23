package com.project.ohouclonecoding.controller;

import com.project.ohouclonecoding.dto.CommentRequestDto;
import com.project.ohouclonecoding.dto.CommentResponseDto;
import com.project.ohouclonecoding.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{postid}")
    public CommentResponseDto createComment(@PathVariable Long postid, @RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(postid, requestDto);
    }

    @PutMapping("/comment/{commentid}")
    public CommentResponseDto updateComment() {
        return commentService.updateComment();
    }

    @DeleteMapping("/comment/{commentid}")
    public void deleteComment() {
        return;commentService.deleteComment();
    }

}
