package com.project.ohouclonecoding.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageMangerService {
    //default: 원하는 명세만 구현 가능
    default String uploadImageFile(MultipartFile file) throws IOException{
        return null;};

    default void deleteImageFile(String currentStoredImageName){};

    default String updateImageFile(String currentStoredImageName, MultipartFile file) throws IOException{
        return null;};
}
