package com.project.ohouclonecoding.service;


import com.project.ohouclonecoding.dto.comment.CommentRequestDto;
import com.project.ohouclonecoding.dto.comment.CommentResponseDto;
import com.project.ohouclonecoding.entity.Comment;
import com.project.ohouclonecoding.entity.Post;
import com.project.ohouclonecoding.entity.User;
import com.project.ohouclonecoding.repository.CommentRepository;
import com.project.ohouclonecoding.repository.post.PostRepository;
import com.project.ohouclonecoding.repository.UserRepository;
import com.project.ohouclonecoding.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;




    //댓글 생성
    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, User user) {
        // board 가 있는지 확인
        // requestDto.getBoardId(); => board 찾아봐야하죠. => 찾아서 없으면 예외처리
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 게시글 입니다.")
        );
        String comment = requestDto.getComment();
        Comment newComment = commentRepository.save(new Comment(comment, post, user));

        return new CommentResponseDto(newComment);
    }

    //댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );

        if (!(comment.getUser().getNickname().equals(user.getNickname())) && !(user.getRole().getAuthority().equals("ROLE_ADMIN"))){
            throw new IllegalArgumentException("작성자와 관리자만 수정할 수 있습니다.");
        }
        String updateComment = requestDto.getComment();
        comment.update(updateComment);
        return new CommentResponseDto(comment);
    }

    //댓글 삭제
    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );

        if (!(comment.getUser().getNickname().equals(user.getNickname())) && !(user.getRole().getAuthority().equals("ROLE_ADMIN"))){
            throw new IllegalArgumentException("작성자와 관리자만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);

    }
}
