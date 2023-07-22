package com.project.ohouclonecoding.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private String errorMsg;

    public LoginResponseDto(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public LoginResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

    }
}
