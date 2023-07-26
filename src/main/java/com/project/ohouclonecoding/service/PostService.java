package com.project.ohouclonecoding.service;

import com.project.ohouclonecoding.dto.comment.CommentResponseDto;
import com.project.ohouclonecoding.dto.post.AllPostResponseDto;
import com.project.ohouclonecoding.dto.post.OnePostResponseDto;
import com.project.ohouclonecoding.dto.post.PostRequestDto;
import com.project.ohouclonecoding.entity.Comment;
import com.project.ohouclonecoding.entity.Post;
import com.project.ohouclonecoding.entity.User;
import com.project.ohouclonecoding.repository.CommentLikeRepository;
import com.project.ohouclonecoding.repository.PostLikeRepository;
import com.project.ohouclonecoding.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    @Qualifier(value = "postS3ImageService")
    private final ImageMangerService postS3Service;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    //게시글 생성
    @Transactional
    public void createPost(PostRequestDto requestDto, MultipartFile postImg, User user) throws IOException {

        //S3 이미지 저장
        String storedPostName = postS3Service.uploadImageFile(postImg);

        try {
            // Set the nickname from the User entity
            String nickname = user.getNickname();
            postRepository.save(new Post(user, nickname, requestDto.getContent(), storedPostName));
        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            throw new IllegalArgumentException("저장에 실패하였습니다");
        }
    }

    //게시글 전체조회(Home - 페이징 8개)
    public List<AllPostResponseDto> getHomePost() {
        return postRepository.findAll().stream().map(AllPostResponseDto::new).toList();
    }


    //게시글 전체조회(집사진 - 더 많이이이)
    public List<AllPostResponseDto> getPost() {
        return postRepository.findAll().stream().map(AllPostResponseDto::new).toList();
    }

    //게시물 상세조회
    @Transactional
    public OnePostResponseDto getOnePost(Long postId, User user) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다."));

        findPost.increaseViewCount();


        boolean hasLikedPost = false;

        if (postLikeRepository.existsLikeByPostAndUser(findPost, user)){
            hasLikedPost = true;
        }

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : findPost.getCommentList()) {
            // 해당 유저가 댓글에 좋아요를 했는지 확인
            boolean hasCommentLiked = commentLikeRepository.existsLikeByCommentAndUser(comment, user);
            commentResponseDtoList.add(new CommentResponseDto(comment, hasCommentLiked));
        }

        return new OnePostResponseDto(findPost, hasLikedPost, commentResponseDtoList);
    }


    //게시글 수정
    @Transactional
    public void updatePost(Long postId, PostRequestDto requestDto, User user) {
        Post post = findPost(postId);
        if (!(post.getNickname().equals(user.getNickname()) && !(user.getRole().getAuthority().equals("ROLE_ADMIN")))){
            throw new IllegalArgumentException("작성자와 관리자만 수정할 수 있습니다.");
        }
        String content = requestDto.getContent();
        post.update(content);
    }


    //게시글 삭제
    @Transactional
    public void deletePost(Long postId, User user) {
        Post post = findPost(postId);

        if (!(post.getNickname().equals(user.getNickname()) && !(user.getRole().getAuthority().equals("ROLE_ADMIN")))){
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
