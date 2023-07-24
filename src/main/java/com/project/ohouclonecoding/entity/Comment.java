package com.project.ohouclonecoding.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.ohouclonecoding.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "comment")
    private String comment;

    @Column
    private String nickname; //사용자 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Comment(CommentRequestDto requestDto, String nickname, Post post, User user) {
        this.nickname = nickname;
        this.comment = requestDto.getComment();
        this.post = post; // 3 post
        this.user = user;
        post.getCommentList().add(this);   // post에서도 commentList에 넣어주어야 조회가 가능.
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
