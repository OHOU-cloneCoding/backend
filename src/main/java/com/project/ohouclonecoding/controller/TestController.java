package com.project.ohouclonecoding.controller;

import com.project.ohouclonecoding.security.UserDetailsImpl;
import com.project.ohouclonecoding.service.CrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TestController {
    private final CrawlingService crawlingService;
    @PostMapping("/test")
    public String test(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return new String("hi");
    }
    @GetMapping("/test")
    public void crawling() throws IOException {
        crawlingService.crawling();
    }
}
