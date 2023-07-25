package com.project.ohouclonecoding.dto;

import lombok.Getter;

@Getter
public class MessageResponseDto {
    private String msg;

    public MessageResponseDto(String msg){
        this.msg = msg;
    }
}
