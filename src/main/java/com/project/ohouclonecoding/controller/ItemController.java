package com.project.ohouclonecoding.controller;


import com.project.ohouclonecoding.service.ImageMangerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ItemController {

    @Qualifier("itemS3ImageService")
    private final ImageMangerService itemS3Service;
}
