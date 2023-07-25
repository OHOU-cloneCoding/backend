package com.project.ohouclonecoding.dto.comment;

import com.project.ohouclonecoding.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private String nickname; // 작성자 이름
    private String comment; // 댓글 내용
    private long commentLikeSize;
    private LocalDateTime createdAt; // 작성 시간
    private boolean hasCommentLiked = false;


    public CommentResponseDto(Comment newComment) {
        this.nickname = newComment.getUser().getNickname();
        this.comment = newComment.getComment();
        this.commentLikeSize = newComment.getCommentLikes().size();
        this.createdAt = newComment.getCreatedAt();
    }

    public CommentResponseDto(Comment newComment, boolean hasCommentLiked) {
        this.nickname = newComment.getUser().getNickname();
        this.comment = newComment.getComment();
        this.commentLikeSize = newComment.getCommentLikes().size();
        this.createdAt = newComment.getCreatedAt();
        this.hasCommentLiked = hasCommentLiked;
    }
}
