package com.project.ohouclonecoding.controller;


import com.project.ohouclonecoding.dto.PostRequestDto;
import com.project.ohouclonecoding.dto.PostResponseDto;
import com.project.ohouclonecoding.entity.User;
import com.project.ohouclonecoding.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    //게시글 생성
    @PostMapping("")
    public PostResponseDto createPost(
            @RequestPart PostRequestDto postRequestDto,
            @RequestPart("postImg") MultipartFile postImg

    ) throws IOException {
        return postService.createPost(postRequestDto,postImg);
    }

    //게시글 전체 조회(home - 페이징 8개)
    @GetMapping("/home")
    public List<PostResponseDto> getHomePost(){
        return postService.getHomePost();
    }

    //게시글 전체 조회(집사진)
    @GetMapping("")
    public List<PostResponseDto> getPost(){
        return postService.getPost();
    }

    //게시글 선택 조회
    @GetMapping("/{postId}")
    public PostResponseDto getOnePost(@PathVariable("postId") Long postId){
        return postService.getOnePost(postId);

    }

    //게시글 수정
    @PutMapping("/{postId}")
    public String updatePost(@PathVariable("postId") Long postId,
                                      @RequestBody PostRequestDto requestDto,
                                      HttpServletRequest request){
        User user = (User)request.getAttribute("user");
        postService.updatePost(postId, requestDto, user);

        return "게시글 수정 성공";
    }

    //게시글 삭제
    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable("postId") Long postId,
                             HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        postService.deleteMemo(postId, user);

        return "게시글 삭제 성공";
    }
}
