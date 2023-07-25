package com.project.ohouclonecoding.controller;


import com.project.ohouclonecoding.dto.MessageResponseDto;
import com.project.ohouclonecoding.dto.PostSearchDto;
import com.project.ohouclonecoding.dto.post.AllPostResponseDto;
import com.project.ohouclonecoding.dto.post.OnePostResponseDto;
import com.project.ohouclonecoding.dto.post.PostRequestDto;
import com.project.ohouclonecoding.entity.User;
import com.project.ohouclonecoding.repository.post.PostRepository;
import com.project.ohouclonecoding.security.UserDetailsImpl;
import com.project.ohouclonecoding.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final PostRepository postRepository;

    //게시글 생성
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDto createPost(
            @RequestPart PostRequestDto postRequestDto,
            @RequestPart("postImg") MultipartFile postImg,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) throws IOException {
        User user = userDetails.getUser();
        postService.createPost(postRequestDto,postImg,user);

        return new MessageResponseDto("게시글 생성 성공");
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
        User user = userDetails.getUser();
        return postService.getOnePost(postId, user);
    }

    //게시글 수정
    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDto updatePost(@PathVariable("postId") Long postId,
                                         @RequestBody PostRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        postService.updatePost(postId, requestDto, user);

        return new MessageResponseDto("게시글 수정 성공");
    }

    //게시글 삭제
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDto deletePost(@PathVariable("postId") Long postId,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();

        postService.deletePost(postId, user);

        return new MessageResponseDto("게시글 삭제 성공");
    }

    @GetMapping("/search")
    public Page<AllPostResponseDto> searchPosts(PostSearchDto condition) {
        return postRepository.searchPosts(condition);
    }
}
