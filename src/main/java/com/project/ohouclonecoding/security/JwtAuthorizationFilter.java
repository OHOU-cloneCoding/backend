package com.project.ohouclonecoding.security;

import com.project.ohouclonecoding.entity.User;
import com.project.ohouclonecoding.jwt.JwtUtil;
import com.project.ohouclonecoding.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    private final UserRepository userRepository;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtUtil.getJwtFromHeader(req, "Access");
        String refreshToken = jwtUtil.getJwtFromHeader(req, "Refresh");
        if(accessToken!= null){
            if(jwtUtil.validateToken(accessToken)){
                setAuthentication(jwtUtil.getNicknameFromToken(accessToken));
            }else if(refreshToken != null){
                boolean isRefreshToken = jwtUtil.refreshTokenValidation(refreshToken);
                if(isRefreshToken){
                    String nickname = jwtUtil.getNicknameFromToken(refreshToken);
                    User user =  userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalArgumentException("잘못된 회원명입니다."));
                    String newAccessToken = jwtUtil.createToken(nickname, user.getRole() , "Access");
                    System.out.println("newAccessToken = " + newAccessToken);
                    res.setHeader("Access_Token", newAccessToken);
                    Claims info = jwtUtil.getUserInfoFromToken(newAccessToken.substring(7));
                    System.out.println("info.getSubject() = " + info.getSubject());
                    setAuthentication(info.getSubject());
                } else{
                    System.out.println(1);
                    throw new IllegalArgumentException("잘못된 토큰입니다.");
                }
            }
        }
        filterChain.doFilter(req, res);
    }
    // 인증 처리
    public void setAuthentication(String nickname) {
        System.out.println("setAuthentication");
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(nickname);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String nickname) {
        System.out.println("createAuthentication");
        UserDetails userDetails = userDetailsService.loadUserByUsername(nickname);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}