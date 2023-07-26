package com.project.ohouclonecoding.service;

import com.project.ohouclonecoding.entity.Post;
import com.project.ohouclonecoding.entity.PostLike;
import com.project.ohouclonecoding.entity.User;
import com.project.ohouclonecoding.repository.PostLikeRepository;
import com.project.ohouclonecoding.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    public void postLike(Long postId, User user){
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if(!postLikeRepository.existsLikeByPostAndUser(post, user)) {
            PostLike like = new PostLike(post, user);
            postLikeRepository.save(like);
        } else {
            PostLike like = postLikeRepository.findByPostAndUser(post, user).orElseThrow(
                    ()-> new IllegalArgumentException("좋아요에 대한 정보가 존재하지 않습니다."));
            postLikeRepository.delete(like);
        }
    }
}
