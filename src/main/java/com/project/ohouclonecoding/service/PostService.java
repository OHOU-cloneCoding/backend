package com.project.ohouclonecoding.service;

import com.project.ohouclonecoding.dto.PostRequestDto;
import com.project.ohouclonecoding.dto.PostResponseDto;
import com.project.ohouclonecoding.entity.Post;
import com.project.ohouclonecoding.entity.User;
import com.project.ohouclonecoding.repository.PostRepository;
import com.project.ohouclonecoding.repository.UserRepository;
import com.project.ohouclonecoding.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    @Qualifier(value = "postS3ImageService")
    private final ImageMangerService postS3Service;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, MultipartFile postImg, UserDetailsImpl userDetails) throws IOException {
        User user = userDetails.getUser();

        //S3 이미지 저장
        String storedPostName = postS3Service.uploadImageFile(postImg);

        try {
            // Set the nickname from the User entity
            String nickname = user.getNickname();
            Post savedPost = postRepository.save(new Post(user, nickname, requestDto.getContent(), storedPostName));
            return new PostResponseDto(savedPost);
        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            throw new IllegalArgumentException("저장에 실패하였습니다");
        }
    }

    //게시글 전체조회(Home - 페이징 8개)
    public List<PostResponseDto> getHomePost() {
        return postRepository.findAll().stream().map(PostResponseDto::new).toList();
    }


    //게시글 전체조회(집사진 - 더 많이이이)
    public List<PostResponseDto> getPost() {
        return postRepository.findAll().stream().map(PostResponseDto::new).toList();
    }

    public PostResponseDto getOnePost(Long postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다."));

        return new PostResponseDto(findPost);
    }

    //게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, UserDetailsImpl userDetails) {
        Post post = findPost(postId);

        if (!(post.getNickname().equals(userDetails.getUser().getNickname()) && !(userDetails.getUser().getRole().getAuthority().equals("ROLE_ADMIN")))){
            throw new IllegalArgumentException("작성자와 관리자만 수정할 수 있습니다.");
        }
        post.update(requestDto);
        return new PostResponseDto(post);
    }


    //게시글 삭제
    @Transactional
    public void deleteMemo(Long postId, UserDetailsImpl userDetails) {
        Post post = findPost(postId);

        if (!(post.getNickname().equals(userDetails.getUser().getNickname()) && !(userDetails.getUser().getRole().getAuthority().equals("ROLE_ADMIN")))){
            throw new IllegalArgumentException("작성자와 관리자만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }


    private Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }


}
