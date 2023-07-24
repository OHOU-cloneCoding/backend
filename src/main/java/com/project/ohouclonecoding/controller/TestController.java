package com.project.ohouclonecoding.controller;

import com.project.ohouclonecoding.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TestController {
    @PostMapping("/test")
    public String test(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return new String("hi");
    }
}
