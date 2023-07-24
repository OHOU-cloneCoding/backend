package com.project.ohouclonecoding.dto.post;


import lombok.Getter;

@Getter
public class PostRequestDto {
    private String content;
    private String postImg;



    public PostRequestDto(String content, String postImg){
        this.content = content;
        this.postImg = postImg;
    }
}
