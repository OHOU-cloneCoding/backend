package com.project.ohouclonecoding.dto.post;

import com.project.ohouclonecoding.dto.comment.CommentResponseDto;
import com.project.ohouclonecoding.entity.Comment;
import com.project.ohouclonecoding.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OnePostResponseDto {
    private Long postId;
    private String nickname;
    private String content;
    private String postImg;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();
    private int postLikeSize;
    private int commentSize;
    private boolean hasLiked;   // 지금 글을 조회하고 있는 사용자가 본인이 좋아요를 눌렀는지 확인
    private long postViewCount;


    public OnePostResponseDto(Post post, boolean hasLikedPost, List<CommentResponseDto> commentResponseDtoList) {
        this.postId = post.getPostId();
        this.nickname = post.getNickname();
        this.content = post.getContent();
        this.postImg = post.getPostImg();
        this.createdAt = post.getCreatedAt();

//        for (Comment comment : post.getCommentList()) {
//            this.commentList.add(new CommentResponseDto(comment));
//        }
        this.commentList = commentResponseDtoList;

        this.postLikeSize = post.getLike().size();
        this.commentSize = post.getCommentList().size();
        this.hasLiked = hasLikedPost;
        this.postViewCount = post.getPostViewCount();
    }

}
