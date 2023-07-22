package com.project.ohouclonecoding.controller;


import com.project.ohouclonecoding.service.ImageMangerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    @Qualifier(value = "postS3ImageService")
    private final ImageMangerService postS3ImageService;


}
