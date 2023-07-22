package com.project.ohouclonecoding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Qualifier(value = "postS3ImageService")
public class PostS3ImageService implements ImageMangerService{

}
