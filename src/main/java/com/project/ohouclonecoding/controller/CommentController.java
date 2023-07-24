package com.project.ohouclonecoding.controller;

import com.project.ohouclonecoding.dto.comment.CommentRequestDto;
import com.project.ohouclonecoding.dto.comment.CommentResponseDto;
import com.project.ohouclonecoding.security.UserDetailsImpl;
import com.project.ohouclonecoding.service.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Transactional
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping("/{postId}/comments")
    public CommentResponseDto createComment(@PathVariable Long postId,
                                            @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return commentService.createComment(postId, requestDto, userDetails);
    }

    //댓글 수정
    @PutMapping("/{postId}/comments/{commentId}")
    public String updateComment(@PathVariable Long commentId,
                                @RequestBody CommentRequestDto requestDto,
                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.updateComment(commentId, requestDto, userDetails);

        return "댓글 수정 성공";
    }


    //댓글 삭제
    @DeleteMapping("/{postId}/comments/{commentId}")
    public String updageComment(@PathVariable Long commentId,
                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.deleteComment(commentId, userDetails);
        return "댓글 삭제 성공";
    }
}
