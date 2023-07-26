package com.project.ohouclonecoding.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Post extends Auditing {
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

    private long postViewCount = 0;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.REMOVE})
    private List<PostLike> like = new ArrayList<>();


    public Post(User user, String nickname, String content, String storedPostName) {
        this.user = user;
        this.nickname = nickname;
        this.content = content;
        this.postImg = storedPostName;
    }


    public void update(String content) {
        this.content = content;
    }

    public void increaseViewCount() {
        this.postViewCount += 1;
    }
}
