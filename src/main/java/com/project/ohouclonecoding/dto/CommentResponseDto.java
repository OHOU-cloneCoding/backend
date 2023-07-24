package com.project.ohouclonecoding.dto;

import com.project.ohouclonecoding.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {
    private String nickname; // 작성자 이름
    private String comment; // 댓글 내용
    private LocalDateTime createdAt; // 작성 시간


    public CommentResponseDto(Comment newComment) {
        this.nickname = newComment.getNickname();
        this.comment = newComment.getComment();
        this.createdAt = newComment.getCreatedAt();
    }
}
