package com.project.ohouclonecoding.service;

import com.project.ohouclonecoding.entity.Comment;
import com.project.ohouclonecoding.entity.Like;
import com.project.ohouclonecoding.entity.Post;
import com.project.ohouclonecoding.entity.User;
import com.project.ohouclonecoding.repository.CommentRepository;
import com.project.ohouclonecoding.repository.LikeRepository;
import com.project.ohouclonecoding.repository.PostRepository;
import com.project.ohouclonecoding.repository.UserRepository;
import com.project.ohouclonecoding.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public void getPostLike(Long postId, UserDetailsImpl userDetails){
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        User user = userDetails.getUser();

        if(!likeRepository.existsLikeByPostAndUser(post, user)) {
            Like like = new Like(post, user);
            likeRepository.save(like);
        } else {
            Like like = likeRepository.findByPostAndUser(post, user).orElseThrow(
                    ()-> new IllegalArgumentException("좋아요에 대한 정보가 존재하지 않습니다."));
            likeRepository.delete(like);
        }
    }

    public void getCommentLike(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        User user = userDetails.getUser();

        if(!likeRepository.existsLikeByCommentAndUser(comment, user)){
            Like like = new Like(comment, user);
            likeRepository.save(like);
        } else {
            Like like = likeRepository.findByCommentAndUser(comment, user).orElseThrow(
                    ()-> new IllegalArgumentException("좋아요에 대한 정보가 존재하지 않습니다."));
            likeRepository.delete(like);
        }
    }
}
