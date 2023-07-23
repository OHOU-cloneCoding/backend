package com.project.ohouclonecoding.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long commentId;

    private String nickname;

    private String comment;

    private LocalDateTime createdAt;
}
