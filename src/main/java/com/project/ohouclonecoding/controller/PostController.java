package com.project.ohouclonecoding.controller;


import com.project.ohouclonecoding.dto.post.AllPostResponseDto;
import com.project.ohouclonecoding.dto.post.OnePostResponseDto;
import com.project.ohouclonecoding.dto.post.PostRequestDto;
import com.project.ohouclonecoding.security.UserDetailsImpl;
import com.project.ohouclonecoding.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    //게시글 생성
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void createPost(
            @RequestPart PostRequestDto postRequestDto,
            @RequestPart("postImg") MultipartFile postImg,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) throws IOException {
        postService.createPost(postRequestDto,postImg,userDetails);
    }

    //게시글 전체 조회(home - 페이징 8개)
    @GetMapping("/home")
    public List<AllPostResponseDto> getHomePost(){
        return postService.getHomePost();
    }

    //게시글 전체 조회(집사진)
    @GetMapping("")
    public List<AllPostResponseDto> getPost(){
        return postService.getPost();
    }

    //게시글 상세 조회
    @GetMapping("/{postId}")
    public OnePostResponseDto getOnePost(@PathVariable("postId") Long postId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.getOnePost(postId, userDetails);
    }

    //게시글 수정
    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePost(@PathVariable("postId") Long postId,
                             @RequestBody PostRequestDto requestDto,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
            postService.updatePost(postId, requestDto, userDetails);
    }

    //게시글 삭제
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable("postId") Long postId,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){

        postService.deletePost(postId, userDetails);
    }
}
