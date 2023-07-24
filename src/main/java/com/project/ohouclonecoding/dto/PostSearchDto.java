package com.project.ohouclonecoding.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchDto {
    private String keyword;

    private int size = 10;
    private int page = 1;
    private boolean isASC = true;
    private String orderField = "postId"; // content, createdAt
}
