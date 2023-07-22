package com.project.ohouclonecoding.entity;

import com.project.ohouclonecoding.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private String postImg;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Post(String nickname, String content, String storedPostName) {
        this.nickname = nickname;
        this.content = content;
        this.postImg = storedPostName;
    }

    public void update(PostRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
