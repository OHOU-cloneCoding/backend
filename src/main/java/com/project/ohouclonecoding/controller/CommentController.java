package com.project.ohouclonecoding.controller;

import com.project.ohouclonecoding.dto.MessageResponseDto;
import com.project.ohouclonecoding.dto.comment.CommentRequestDto;
import com.project.ohouclonecoding.entity.User;
import com.project.ohouclonecoding.security.UserDetailsImpl;
import com.project.ohouclonecoding.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping("/{postId}/comments")
    public MessageResponseDto createComment(@PathVariable Long postId,
                                            @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        commentService.createComment(postId, requestDto, user);

        return new MessageResponseDto("댓글 생성 성공");
    }

    //댓글 수정
    @PutMapping("/{postId}/comments/{commentId}")
    public MessageResponseDto updateComment(@PathVariable Long commentId,
                                            @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        commentService.updateComment(commentId, requestDto, user);

        return new MessageResponseDto("댓글 수정 성공");
    }


    //댓글 삭제
    @DeleteMapping("/{postId}/comments/{commentId}")
    public MessageResponseDto updageComment(@PathVariable Long commentId,
                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        commentService.deleteComment(commentId, user);
        return new MessageResponseDto("댓글 삭제 성공");
    }
}
