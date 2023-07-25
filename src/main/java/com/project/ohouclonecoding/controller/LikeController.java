package com.project.ohouclonecoding.controller;

import com.project.ohouclonecoding.dto.MessageResponseDto;
import com.project.ohouclonecoding.entity.User;
import com.project.ohouclonecoding.security.UserDetailsImpl;
import com.project.ohouclonecoding.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Transactional
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/posts/{postId}/like")
    public void getPostLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        likeService.getPostLike(postId, user);
    }

    @PostMapping("/posts/{postId}/comments/{commentId}/like")
    public void getCommentLike(@PathVariable Long postId,@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        likeService.getCommentLike(postId, commentId, user);
    }
}
