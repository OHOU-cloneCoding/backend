package com.project.ohouclonecoding.dto;

import com.project.ohouclonecoding.entity.Comment;
import com.project.ohouclonecoding.entity.Post;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long postId;
    private String nickname;
    private String content;
    private String postImg;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();



    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.nickname = post.getNickname();
        this.content = post.getContent();
        this.postImg = post.getPostImg();
        this.createdAt = post.getCreatedAt();
        for (Comment comment : post.getCommentList()) {
            this.commentList.add(new CommentResponseDto(comment));
        }
    }
}
