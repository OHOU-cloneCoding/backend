package com.project.ohouclonecoding.controller;

import com.project.ohouclonecoding.dto.CommentRequestDto;
import com.project.ohouclonecoding.dto.CommentResponseDto;
import com.project.ohouclonecoding.security.UserDetailsImpl;
import com.project.ohouclonecoding.service.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
@Transactional
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping("/{postId}")
    public CommentResponseDto createComment(@PathVariable Long postId,
                                            @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return commentService.createComment(postId, requestDto, userDetails);
    }

    //댓글 수정
    @PutMapping("/{commentId}")
    public String updateComment(@PathVariable Long commentId,
                                @RequestBody CommentRequestDto requestDto,
                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.updateComment(commentId, requestDto, userDetails);

        return "댓글 수정 성공";
    }


    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public String updageComment(@PathVariable Long commentId,
                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.deleteComment(commentId, userDetails);
        return "댓글 삭제 성공";
    }
}
