package com.project.ohouclonecoding.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


public interface ImageMangerService {
    //default: 원하는 명세만 구현 가능
    default String uploadImageFile(MultipartFile file) throws IOException{
        return null;}

    default void deleteImageFile(String currentStoredImageName){}


    class Functions{
        static String getKeyFromImageName(String storeImageName, String bucketFolder) {
            return bucketFolder + "/" + storeImageName;
        }
         static String extractKeyFromFullPath(String currentStoredImageName) {
            return currentStoredImageName.substring(currentStoredImageName.lastIndexOf("/") - 11);
        }

        static String createStoreImageName(String originalImageName) {
            String extName = extractExt(originalImageName);
            String uuid = UUID.randomUUID().toString();
            return uuid + "." + extName;
        }

        static String extractExt(String originalImageName) {
            int pos = originalImageName.lastIndexOf(".");
            String extName = originalImageName.substring(pos + 1);
            return extName;
        }
    }
}
