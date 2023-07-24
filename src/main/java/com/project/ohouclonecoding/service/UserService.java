package com.project.ohouclonecoding.service;

import com.project.ohouclonecoding.dto.LoginRequestDto;
import com.project.ohouclonecoding.dto.LoginResponseDto;
import com.project.ohouclonecoding.dto.SignupRequestDto;
import com.project.ohouclonecoding.dto.TokenDto;
import com.project.ohouclonecoding.entity.RefreshToken;
import com.project.ohouclonecoding.entity.User;
import com.project.ohouclonecoding.entity.UserRoleEnum;
import com.project.ohouclonecoding.jwt.JwtUtil;
import com.project.ohouclonecoding.repository.RefreshTokenRepository;
import com.project.ohouclonecoding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signup(SignupRequestDto requestDto) {
        String nickname = requestDto.getNickname();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByNickname(nickname);
        if (!(checkUsername.isEmpty())) {
            throw new IllegalArgumentException("중복된 회원명입니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if(requestDto.isAdmin()){
            if(!ADMIN_TOKEN.equals(requestDto.getAdminToken())){
                throw new IllegalArgumentException("관리자 암호가 다릅니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(nickname, password, email, role);
        userRepository.save(user);
    }
    public LoginResponseDto login(LoginRequestDto requestDto){
        User user = userRepository.findByNickname(requestDto.getNickname()).orElseThrow(() ->
                new IllegalArgumentException("가입되지 않은 이름입니다."));
        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        TokenDto tokenDto = jwtUtil.createAllToken(user.getNickname(), user.getRole());
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByNickname(user.getNickname());
        if(refreshToken.isPresent()){
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else{
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), user.getNickname());
            refreshTokenRepository.save(newToken);
        }
        return new LoginResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());

    }
}
