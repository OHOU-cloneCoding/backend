package com.project.ohouclonecoding.dto;


import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class PostRequestDto {
    private String content;
    private String postImg;



    public PostRequestDto(String content, String postImg){
        this.content = content;
        this.postImg = postImg;
    }
}
