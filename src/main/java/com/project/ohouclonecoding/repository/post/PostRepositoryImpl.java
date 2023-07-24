package com.project.ohouclonecoding.repository.post;

import com.project.ohouclonecoding.dto.PostSearchDto;
import com.project.ohouclonecoding.dto.post.AllPostResponseDto;
import com.project.ohouclonecoding.entity.QComment;
import com.project.ohouclonecoding.entity.QPostLike;
import com.project.ohouclonecoding.entity.QUser;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.project.ohouclonecoding.entity.QComment.comment1;
import static com.project.ohouclonecoding.entity.QItem.item;
import static com.project.ohouclonecoding.entity.QPost.post;
import static com.project.ohouclonecoding.entity.QPostLike.postLike;
import static com.project.ohouclonecoding.entity.QUser.user;
import static org.springframework.util.StringUtils.*;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<AllPostResponseDto> searchPosts(PostSearchDto condition) {

        PageRequest pageRequest = PageRequest.of(condition.getPage() - 1, condition.getSize());

        List<AllPostResponseDto> content = jpaQueryFactory
                .select(
                        Projections.fields(AllPostResponseDto.class,
                                post.postId,
                                post.user.nickname,
                                post.content,
                                post.postImg,
                                post.createdAt,
                                post.like.size().as("likeSize"),
                                post.commentList.size().as("commentSize"),
                                post.postViewCount
                        ))
                .from(post)
                .leftJoin(post.user, user)
                .where(
                        containKeywordInContent(condition.getKeyword())
                )
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .orderBy(orderByMethod(condition.getOrderField(), condition.isASC()))
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(post.count())
                .from(post)
                .leftJoin(post.user, user)
                .where(
                        containKeywordInContent(condition.getKeyword())
                );

        return PageableExecutionUtils.getPage(content, pageRequest, countQuery::fetchOne);
    }

    private OrderSpecifier<?> orderByMethod(String orderField, boolean asc) {
        if (!hasText(orderField)) {
            return post.postId.desc();
        }
        switch (orderField) {
            case "content" -> {
                return asc ? post.content.asc() : post.content.desc();
            }
            case "createdAt" -> {
                return asc ? post.createdAt.asc() : post.createdAt.desc();
            }
            default -> {
                return asc ? post.postId.asc() : post.postId.desc();
            }
        }
    }

    private BooleanExpression containKeywordInContent(String keyword) {
        return hasText(keyword) ? post.content.contains(keyword) : null;

    }
}
