package com.project.ohouclonecoding.jwt;

import com.project.ohouclonecoding.dto.TokenDto;
import com.project.ohouclonecoding.entity.RefreshToken;
import com.project.ohouclonecoding.entity.UserRoleEnum;
import com.project.ohouclonecoding.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j(topic = "JwtUtil")
@Component
@RequiredArgsConstructor
public class JwtUtil {
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String ACCESS_HEADER = "Access";
    public static final String REFRESH_HEADER = "Refresh";
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;

    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }
    public TokenDto createAllToken(String email, UserRoleEnum role) {
        return new TokenDto(createToken(email, role, "Access"), createToken(email, role, "Refresh"));
    }


    // 토큰 생성
    public String createToken(String email, UserRoleEnum role, String type) {
        Date date = new Date();

        long time = type.equals("Access") ? 5 * 30 * 1000L : 30 * 30 * 1000L;

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + time)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    // header 에서 JWT 가져오기
    public String getJwtFromHeader(HttpServletRequest request, String type) {
        if(type.equals("Access")){
            String bearerToken = request.getHeader(ACCESS_HEADER);
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
                return bearerToken.substring(7);
            }
        }else {
            String bearerToken = request.getHeader(REFRESH_HEADER);
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
                return bearerToken.substring(7);
            }
        }
        return null;
    }

    // 토큰 검증
    public boolean validateToken(String token, HttpServletResponse res, String type) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            if(type.equals("Access")){
                res.setHeader("AccessTokenError", "Invalid JWT signature.");
                log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
                throw new IllegalArgumentException("유효하지 않는 JWT 서명 입니다.");
            }else {
                res.setHeader("RefreshTokenError", "Invalid JWT signature.");
                log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
                throw new IllegalArgumentException("유효하지 않는 JWT 서명 입니다.");
            }
        } catch (ExpiredJwtException e) {
            if(type.equals("Access")){
                res.setHeader("AccessTokenError", "expired AccessToken.");
            }else {
                res.setHeader("RefreshTokenError", "expired RefreshToken.");
            }
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            if(type.equals("Access")){
                res.setHeader("AccessTokenError", "Unsupported JWT token.");
            }else {
                res.setHeader("RefreshTokenError", "Unsupported JWT token.");
            }
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            if(type.equals("Access")){
                res.setHeader("AccessTokenError", "JWT claims is empty.");
            }else {
                res.setHeader("RefreshTokenError", "JWT claims is empty.");
            }
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }
    public Boolean refreshTokenValidation(String token, HttpServletResponse res, String type) {
        // 1차 토큰 검증
        if(!validateToken(token, res, type))
            return false;
        String email = getUserInfoFromToken(token).getSubject();
        // DB에 저장한 토큰 비교
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmail(email);
        System.out.println("refreshToken = " + refreshToken.isPresent());
        if(refreshToken.isPresent()){
            System.out.println("token = " + token);
            System.out.println("refreshToken = " + refreshToken.get());
            System.out.println("refreshToken.get().getRefreshToken() = " + refreshToken.get().getRefreshToken());
        }
        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken().substring(7));
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
    public String getEmailFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

}