package com.project.ohouclonecoding.dto;


import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class PostRequestDto {
    private String nickname;
    private String content;

    public PostRequestDto(String nickname, String content){
        this.nickname = nickname;
        this.content = content;
    }
}
