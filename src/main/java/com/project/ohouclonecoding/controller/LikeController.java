package com.project.ohouclonecoding.controller;

import com.project.ohouclonecoding.entity.User;
import com.project.ohouclonecoding.entity.UserRoleEnum;
import com.project.ohouclonecoding.repository.UserRepository;
import com.project.ohouclonecoding.security.UserDetailsImpl;
import com.project.ohouclonecoding.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Transactional
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/posts/{postId}/like")
    public void getPostLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){

        likeService.getPostLike(postId, userDetails);
    }
}
