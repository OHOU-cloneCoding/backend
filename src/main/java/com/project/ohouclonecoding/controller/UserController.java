package com.project.ohouclonecoding.controller;

import com.project.ohouclonecoding.dto.LoginRequestDto;
import com.project.ohouclonecoding.dto.LoginResponseDto;
import com.project.ohouclonecoding.dto.MessageResponseDto;
import com.project.ohouclonecoding.dto.SignupRequestDto;
import com.project.ohouclonecoding.email.MailSendServiceImpl;
import com.project.ohouclonecoding.jwt.JwtUtil;
import com.project.ohouclonecoding.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final MailSendServiceImpl mailSendService;

    @PostMapping("/auth/signup")
    @ResponseStatus(code = HttpStatus.OK)
    public MessageResponseDto signup(@RequestBody SignupRequestDto requestDto) {

        userService.signup(requestDto);

        return new MessageResponseDto("회원 가입 성공");
    }

    @PostMapping("/auth/login")
    private MessageResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            LoginResponseDto responseDto = userService.login(loginRequestDto);
            response.addHeader(JwtUtil.ACCESS_HEADER, responseDto.getAccessToken());
            response.addHeader(JwtUtil.REFRESH_HEADER, responseDto.getRefreshToken());
        }catch(Exception e) {
            e.getMessage();
        }

        return new MessageResponseDto("로그인 성공");
            }
    @PostMapping(value = "/mailCheck", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> mailCheck(@RequestBody HashMap<String, Object> user){
        String email = (String) user.get("email");
        String authNum = mailSendService.joinEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(authNum);
    }
}
