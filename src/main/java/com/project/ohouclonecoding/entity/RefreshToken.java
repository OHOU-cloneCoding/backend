package com.project.ohouclonecoding.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String refreshToken;

    @NotBlank
    private String nickname;

    public RefreshToken(String token, String nickname) {
        this.refreshToken = token;
        this.nickname = nickname;
    }
    public RefreshToken updateToken(String token){
        this.refreshToken = token;
        return this;
    }
}
