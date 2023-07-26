package com.project.ohouclonecoding.service;

import com.project.ohouclonecoding.entity.Comment;
import com.project.ohouclonecoding.entity.CommentLike;
import com.project.ohouclonecoding.entity.User;
import com.project.ohouclonecoding.repository.CommentLikeRepository;
import com.project.ohouclonecoding.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public void commentLike(Long postId, Long commentId, User user) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if(!commentLikeRepository.existsLikeByCommentAndUser(comment, user)){
            CommentLike like = new CommentLike(user, comment);
            commentLikeRepository.save(like);
        } else {
            CommentLike like = commentLikeRepository.findByCommentAndUser(comment, user).orElseThrow(
                    ()-> new IllegalArgumentException("좋아요에 대한 정보가 존재하지 않습니다."));
            commentLikeRepository.delete(like);
        }
    }
}
