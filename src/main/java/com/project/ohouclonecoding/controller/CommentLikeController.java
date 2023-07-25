package com.project.ohouclonecoding.controller;

import com.project.ohouclonecoding.entity.User;
import com.project.ohouclonecoding.security.UserDetailsImpl;
import com.project.ohouclonecoding.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/posts/{postId}/comments/{commentId}/like")
    public void commentLike(@PathVariable Long postId,@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        commentLikeService.commentLike(postId, commentId, user);
    }
}
