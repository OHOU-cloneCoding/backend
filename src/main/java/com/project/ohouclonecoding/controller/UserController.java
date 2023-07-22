package com.project.ohouclonecoding.controller;

import com.project.ohouclonecoding.dto.LoginRequestDto;
import com.project.ohouclonecoding.dto.SignupRequestDto;
import com.project.ohouclonecoding.dto.TokenDto;
import com.project.ohouclonecoding.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/signup")
    @ResponseStatus(code = HttpStatus.OK)
    public void signup(@RequestBody SignupRequestDto requestDto, BindingResult bindingResult, HttpServletResponse httpServletResponse) {
        userService.signup(requestDto);
    }

    @PostMapping("/auth/login")
    private void login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            String token = userService.login(loginRequestDto);
            response.addHeader("Access", token);
        }catch(Exception e) {
            e.getMessage();
        }
            }
}
