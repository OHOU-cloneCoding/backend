package com.project.ohouclonecoding.controller;

import com.project.ohouclonecoding.entity.User;
import com.project.ohouclonecoding.security.UserDetailsImpl;
import com.project.ohouclonecoding.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/posts/{postId}/like")
    public void postLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        postLikeService.postLike(postId, user);
    }
}
