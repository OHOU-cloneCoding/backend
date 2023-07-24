package com.project.ohouclonecoding.service;

import com.project.ohouclonecoding.dto.CommentRequestDto;
import com.project.ohouclonecoding.dto.CommentResponseDto;
import com.project.ohouclonecoding.dto.CreateCommentRequestDto;
import com.project.ohouclonecoding.entity.Comment;
import com.project.ohouclonecoding.entity.Post;
import com.project.ohouclonecoding.entity.User;
import com.project.ohouclonecoding.repository.CommentRepository;
import com.project.ohouclonecoding.repository.PostRepository;
import com.project.ohouclonecoding.repository.UserRepository;
import com.project.ohouclonecoding.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository; // 이것과
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDto<List<Comment>> createComment(Long postId, UserDetailsImpl userDetails, CreateCommentRequestDto createCommentRequestDto) throws RuntimeException {
        try {
            Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
            User user = userRepository.findById(userDetails.getUser().getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

            Comment newComment = new Comment(createCommentRequestDto.getComment(), post, user);
            commentRepository.save(newComment);
            List<Comment> responseBody = commentRepository.findByPostPostId(postId);

            return new CommentResponseDto<List<Comment>>(responseBody);
        } catch(RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean updateComment(Long commentId, CreateCommentRequestDto createCommentRequestDto) {
        try {
            Optional<Comment> comment = commentRepository.findById(commentId);
            if(!comment.isPresent()) throw new RuntimeException();
            commentRepository.updateComment(commentId, createCommentRequestDto.getComment());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean removeComment(Long commentId) {
        try {
            commentRepository.deleteById(commentId);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Boolean isValidComment(UserDetailsImpl userDetails, Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if(optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            if(comment.getUser().getUserId() != userDetails.getUser().getUserId()) {
                //comment 소유자가 일치하지 않는 경우.
                System.out.println("소유자 불일치");
                return false;
            }
            // ...
        } else {
            //comment가 존재하지 않는 경우.
            System.out.println("comment 존재하지 않음");
            return false;
        }
        //
        return true;
    }
}
