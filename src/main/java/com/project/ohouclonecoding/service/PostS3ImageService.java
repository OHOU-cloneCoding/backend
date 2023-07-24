package com.project.ohouclonecoding.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
@Qualifier(value = "postS3ImageService")
public class PostS3ImageService implements ImageMangerService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.bucket.post.folder}")
    private String bucketFolder;

    @Override
    public String uploadImageFile(MultipartFile file) throws IOException {
        if (file == null) {
            log.info("file is empty");
            return null;
        }

        // 원본파일의 이미지 이름 뽑아내기
        String fileName = file.getOriginalFilename();

        // uuid.파일확장자로 저장될 이름 만들기
        String storeImageName = ImageMangerService.Functions.createStoreImageName(fileName);

        // 메타데이터
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());   // contentType 지정
        objectMetadata.setContentDisposition("inline");         // inline 으로 변경시, 이미지를 저장하지 않고 링크로 전달하기 위함

        amazonS3Client.putObject(
                new PutObjectRequest(
                        bucket,
                        ImageMangerService.Functions.getKeyFromImageName(storeImageName, bucketFolder), // key ( folder이름 / 파일 ) 생성메소드
                        file.getInputStream(),
                        objectMetadata
                )
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, ImageMangerService.Functions.getKeyFromImageName(storeImageName, bucketFolder)).toString();
    }
}
