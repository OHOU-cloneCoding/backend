package com.project.ohouclonecoding.service;

import com.project.ohouclonecoding.dto.CommentResponseDto;
import com.project.ohouclonecoding.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponseDto createComment
}
