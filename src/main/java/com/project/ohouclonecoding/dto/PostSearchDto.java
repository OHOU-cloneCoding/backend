package com.project.ohouclonecoding.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchDto {
    private String keyword;

    private int size = 10;
    private int page = 1;
    private boolean isASC = false;
    private String orderField = "postId"; // content, createdAt
}
