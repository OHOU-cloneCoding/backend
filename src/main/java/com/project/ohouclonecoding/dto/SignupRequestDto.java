package com.project.ohouclonecoding.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @Email
    private String email;
    private String password;
    private String pwCheck;
    private String nickname;
    private boolean admin = false;
    private String adminToken = "";
}
