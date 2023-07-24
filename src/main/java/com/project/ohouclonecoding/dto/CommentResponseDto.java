package com.project.ohouclonecoding.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto<T> {
    private T data;

    public CommentResponseDto(T data) {
        this.data = data;
    }
}
