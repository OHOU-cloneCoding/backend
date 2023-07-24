package com.project.ohouclonecoding.service;

import com.project.ohouclonecoding.entity.*;
import com.project.ohouclonecoding.repository.CommentLikeRepository;
import com.project.ohouclonecoding.repository.CommentRepository;
import com.project.ohouclonecoding.repository.PostLikeRepository;
import com.project.ohouclonecoding.repository.PostRepository;
import com.project.ohouclonecoding.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public void getPostLike(Long postId, UserDetailsImpl userDetails){
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        User user = userDetails.getUser();

        if(!postLikeRepository.existsLikeByPostAndUser(post, user)) {
            PostLike like = new PostLike(post, user);
            postLikeRepository.save(like);
        } else {
            PostLike like = postLikeRepository.findByPostAndUser(post, user).orElseThrow(
                    ()-> new IllegalArgumentException("좋아요에 대한 정보가 존재하지 않습니다."));
            postLikeRepository.delete(like);
        }
    }

    public void getCommentLike(Long postId, Long commentId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        User user = userDetails.getUser();

        if(!commentLikeRepository.existsLikeByCommentAndUser(comment, user)){
            CommentLike like = new CommentLike(user, post, comment);
            commentLikeRepository.save(like);
        } else {
            CommentLike like = commentLikeRepository.findByCommentAndUser(comment, user).orElseThrow(
                    ()-> new IllegalArgumentException("좋아요에 대한 정보가 존재하지 않습니다."));
            commentLikeRepository.delete(like);
        }
    }
}
