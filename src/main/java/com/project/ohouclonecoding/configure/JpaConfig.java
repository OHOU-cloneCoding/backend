package com.project.ohouclonecoding.configure;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/* 나중에 다른 configuration 파일에 넣을 생각 */
@Configuration
public class JpaConfig {

    @Bean
    public JPAQueryFactory queryFactory(@Autowired EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
